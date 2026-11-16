package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class TipoNuloException extends Exception {
	public TipoNuloException() {
		super("Tipo nao pode ser nulo.");
	}
}
