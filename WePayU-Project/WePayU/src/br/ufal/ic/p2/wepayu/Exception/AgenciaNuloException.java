package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class AgenciaNuloException extends Exception {
	public AgenciaNuloException() {
		super("Agencia nao pode ser nulo.");
	}
}
