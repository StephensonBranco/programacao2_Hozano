package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class ComissaoNulaException extends Exception {
	public ComissaoNulaException() {
		super("Comissao nao pode ser nula.");
	}
}
