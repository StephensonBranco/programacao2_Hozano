package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class ContaCorrenteNuloException extends Exception {
	public ContaCorrenteNuloException() {
		super("Conta corrente nao pode ser nulo.");
	}
}
