package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

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
	public Pagamento calcularBruto(LocalDate dataPagamento) {
	    return Pagamento.apenasBruto(Dinheiro.truncar(fixoDoPeriodo()));
	}
}