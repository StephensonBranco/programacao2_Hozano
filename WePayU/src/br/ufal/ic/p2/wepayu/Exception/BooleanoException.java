package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class BooleanoException extends Exception {
	public BooleanoException() {
		super("Valor deve ser true ou false.");
	}
}
