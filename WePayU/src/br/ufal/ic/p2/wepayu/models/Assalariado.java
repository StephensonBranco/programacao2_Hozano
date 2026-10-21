package br.ufal.ic.p2.wepayu.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import br.ufal.ic.p2.wepayu.Exception.SalarioNegativoException;
import br.ufal.ic.p2.wepayu.util.Dinheiro;

// Recebe no ultimo dia util do mes
public class Assalariado extends Empregado {

	public Assalariado() {}

	public Assalariado(String nome, String endereco, double salario) throws SalarioNegativoException {
		super(nome, endereco, salario);
		setAgendaPagamento("mensal $");
	}

	@Override
	public String getTipo() {
		return "assalariado";
	}

	@Override
	public boolean recebeEm(LocalDate data) {
		return ultimoDiaUtil(data).equals(data);
	}

	@Override
	public int diasDoPeriodo(LocalDate data) {
		return data.lengthOfMonth();
	}

	@Override
	public Pagamento calcularBruto(LocalDate dataPagamento) {
		return Pagamento.apenasBruto(Dinheiro.truncar(getSalario()));
	}

	private static LocalDate ultimoDiaUtil(LocalDate data) {
		LocalDate dia = data.with(TemporalAdjusters.lastDayOfMonth());
		while (dia.getDayOfWeek() == DayOfWeek.SATURDAY || dia.getDayOfWeek() == DayOfWeek.SUNDAY)
			dia = dia.minusDays(1);
		return dia;
	}
}