package br.ufal.ic.p2.wepayu.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.SalarioNegativoException;
import br.ufal.ic.p2.wepayu.util.Datas;
import br.ufal.ic.p2.wepayu.util.Dinheiro;

// Recebe toda sexta-feira (periodo de 7 dias)
public class Horista extends Empregado {
	private Map<String, Double> cartaoDePonto = new LinkedHashMap<>();

	public Horista() {}

	public Horista(String nome, String endereco, double salarioHora) throws SalarioNegativoException {
		super(nome, endereco, salarioHora);
		setAgendaPagamento("semanal 5");
	}

	@Override
	public String getTipo() {
		return "horista";
	}

	public void lancarHoras(String data, double horas) {
		cartaoDePonto.put(data, horas);
	}

	public Map<String, Double> getCartaoDePonto() {
		return new LinkedHashMap<>(cartaoDePonto);
	}

	public void setCartaoDePonto(Map<String, Double> mapa) {
		this.cartaoDePonto = mapa == null ? new LinkedHashMap<>() : new LinkedHashMap<>(mapa);
	}

	// Horas normais (ate 8/dia) ou extras (acima de 8/dia) no intervalo [inicio, fim)
	public double horasTrabalhadas(LocalDate inicio, LocalDate fim, boolean extras) {
		double horas = 0.0;
		for (Map.Entry<String, Double> entry : cartaoDePonto.entrySet()) {
			LocalDate d = Datas.parse(entry.getKey());
			if (!d.isBefore(inicio) && d.isBefore(fim)) {
				double doDia = entry.getValue();
				horas += extras ? Math.max(doDia - 8, 0) : Math.min(doDia, 8);
			}
		}
		return horas;
	}

	@Override
	public boolean recebeEm(LocalDate data) {
		return data.getDayOfWeek() == DayOfWeek.FRIDAY;
	}

	@Override
	public int diasDoPeriodo(LocalDate data) {
		return 7;
	}

	// So e considerado contratado a partir do primeiro cartao lancado
	@Override
	public boolean contratadoEm(LocalDate data) {
		return cartaoDePonto.keySet().stream().anyMatch(k -> !Datas.parse(k).isAfter(data));
	}

	// periodo (sextaAnterior, dataPagamento] = [sextaAnterior + 1, dataPagamento + 1)
	@Override
	public Pagamento calcularBruto(LocalDate dataPagamento) {
		LocalDate inicio = dataPagamento.minusWeeks(1).plusDays(1);
		LocalDate fim = dataPagamento.plusDays(1);
		double normais = horasTrabalhadas(inicio, fim, false);
		double extras = horasTrabalhadas(inicio, fim, true);
		double salarioHora = getSalario();
		double bruto = Dinheiro.truncar(salarioHora * normais + salarioHora * 1.5 * extras);
		return new Pagamento(normais, extras, 0, 0, 0, bruto);
	}
}