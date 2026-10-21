package br.ufal.ic.p2.wepayu.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.ComissaoNegativaException;
import br.ufal.ic.p2.wepayu.Exception.SalarioNegativoException;
import br.ufal.ic.p2.wepayu.util.Datas;
import br.ufal.ic.p2.wepayu.util.Dinheiro;

// Recebe a cada 2 sextas-feiras (14 dias), a partir de 14/1/2005
public class Comissionado extends Empregado {
	private static final LocalDate PRIMEIRA_QUINZENA = LocalDate.of(2005, 1, 14);

	private double comissao;
	private Map<String, Double> vendasRealizadas = new LinkedHashMap<>();

	public Comissionado() {}

	public Comissionado(String nome, String endereco, double salario, double comissao)
			throws SalarioNegativoException, ComissaoNegativaException {
		super(nome, endereco, salario);
		setComissao(comissao);
		setAgendaPagamento("semanal 2 5");
	}

	@Override
	public String getTipo() {
		return "comissionado";
	}

	public double getComissao() {
		return comissao;
	}

	public final void setComissao(double comissao) throws ComissaoNegativaException {
		if (comissao < 0)
			throw new ComissaoNegativaException();
		this.comissao = comissao;
	}

	public void lancarVenda(String data, double valor) {
		vendasRealizadas.put(data, valor);
	}

	public Map<String, Double> getVendasRealizadas() {
		return new LinkedHashMap<>(vendasRealizadas);
	}

	public void setVendasRealizadas(Map<String, Double> vendasRealizadas) {
		this.vendasRealizadas = vendasRealizadas == null
				? new LinkedHashMap<>()
				: new LinkedHashMap<>(vendasRealizadas);
	}

	// Soma das vendas no intervalo [inicio, fim)
	public double somarVendas(LocalDate inicio, LocalDate fim) {
		return Datas.somarNoIntervalo(vendasRealizadas, inicio, fim);
	}

	@Override
	public boolean recebeEm(LocalDate data) {
		if (data.getDayOfWeek() != DayOfWeek.FRIDAY)
			return false;
		long dias = ChronoUnit.DAYS.between(PRIMEIRA_QUINZENA, data);
		return dias >= 0 && dias % 14 == 0;
	}

	@Override
	public int diasDoPeriodo(LocalDate data) {
		return 14;
	}

	// periodo (sextaRetrasada, dataPagamento]
	@Override
	public Pagamento calcularBruto(LocalDate dataPagamento) {
		LocalDate inicio = dataPagamento.minusWeeks(2).plusDays(1);
		LocalDate fim = dataPagamento.plusDays(1);
		double vendas = 0.0, valorComissao = 0.0;
		for (Map.Entry<String, Double> venda : vendasRealizadas.entrySet()) {
			LocalDate d = Datas.parse(venda.getKey());
			if (!d.isBefore(inicio) && d.isBefore(fim)) {
				vendas += venda.getValue();
				valorComissao += Dinheiro.truncar(venda.getValue() * comissao);
			}
		}
		double fixo = Dinheiro.truncar(getSalario() * 12.0 / 26.0);
		valorComissao = Dinheiro.truncar(valorComissao);
		return new Pagamento(0, 0, fixo, Dinheiro.truncar(vendas), valorComissao, Dinheiro.truncar(fixo + valorComissao));
	}
}