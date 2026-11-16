package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class SalarioNegativoException extends Exception {
	public SalarioNegativoException() {
		super("Salario deve ser nao-negativo.");
	}
}
