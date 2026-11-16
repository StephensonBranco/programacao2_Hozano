package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class SalarioNuloException extends Exception {
	public SalarioNuloException() {
		super("Salario nao pode ser nulo.");
	}
}
