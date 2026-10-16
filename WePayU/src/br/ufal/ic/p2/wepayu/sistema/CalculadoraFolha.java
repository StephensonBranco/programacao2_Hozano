package br.ufal.ic.p2.wepayu.sistema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Pagamento;
import br.ufal.ic.p2.wepayu.util.Datas;
import br.ufal.ic.p2.wepayu.util.Dinheiro;

/*
 * Orquestra a folha: quem recebe, quanto (cada Empregado sabe calcular o proprio bruto)
 * e os descontos sindicais, que dependem do repositorio (debitos e taxas ja cobradas).
 */
public class CalculadoraFolha {

	public record LinhaFolha(Empregado empregado, Pagamento pagamento, double descontos, double liquido) {}

	private final RepositorioEmpregados repo;

	public CalculadoraFolha(RepositorioEmpregados repo) {
		this.repo = repo;
	}

	/*
	 * Desconta a taxa sindical do periodo (taxa diaria * dias) mais o debito acumulado de pagamentos anteriores,
	 * e depois as taxas de servico pendentes (da mais antiga para a mais nova). O contracheque nunca fica
	 * negativo: o que nao coube da taxa sindical vira debito e o que nao coube das taxas de servico continua pendente.
	 */
	private double calcularDescontosSindicais(String id, Empregado emp, LocalDate dataPagamento, double bruto) {
		if (!emp.getSindicalizado() || !emp.contratadoEm(dataPagamento))
			return 0.0;

		double taxaPeriodo = emp.getTaxaSindical() == null ? 0.0 : emp.getTaxaSindical() * emp.diasDoPeriodo(dataPagamento);
		double devido = emp.getDebitoSindical() + taxaPeriodo;
		double desconto = Dinheiro.truncar(Math.min(devido, Math.max(bruto, 0.0)));
		emp.setDebitoSindical(Math.round((devido - desconto) * 100.0) / 100.0);

		double disponivel = bruto - desconto;
		Map<String, Double> taxas = emp.getTaxasServico();
		List<String> datas = new ArrayList<>(taxas.keySet());
		datas.sort(Comparator.comparing(Datas::parse));

		for (String chave : datas) {
			if (disponivel <= 0.0 || Datas.parse(chave).isAfter(dataPagamento))
				break;
			double restante = taxas.get(chave) - repo.getCobrado(id, chave);
			if (restante <= 0.004)
				continue;
			double usado = Math.min(restante, disponivel);
			desconto += usado;
			disponivel -= usado;
			repo.registrarCobranca(id, chave, usado);
		}
		return Dinheiro.truncar(desconto);
	}

	// Total (bruto) que seria pago no dia, sem rodar a folha
	public double totalFolha(LocalDate data) {
		double total = 0.0;
		for (Empregado emp : repo.todos().values())
			if (emp.recebeEm(data))
				total += emp.calcularBruto(data).bruto();
		return total;
	}

	// Calcula o contracheque de quem recebe no dia (aplica os descontos e atualiza debitos/cobrancas)
	public List<LinhaFolha> calcularFolha(LocalDate data) {
		List<LinhaFolha> linhas = new ArrayList<>();
		for (Map.Entry<String, Empregado> entry : repo.todos().entrySet()) {
			Empregado emp = entry.getValue();
			if (!emp.recebeEm(data))
				continue;
			Pagamento pagamento = emp.calcularBruto(data);
			double descontos = calcularDescontosSindicais(entry.getKey(), emp, data, pagamento.bruto());
			linhas.add(new LinhaFolha(emp, pagamento, descontos, Dinheiro.truncar(pagamento.bruto() - descontos)));
		}
		return linhas;
	}
}