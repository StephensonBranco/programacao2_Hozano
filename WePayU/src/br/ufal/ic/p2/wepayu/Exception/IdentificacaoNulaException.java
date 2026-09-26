package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class IdentificacaoNulaException extends Exception {
	public IdentificacaoNulaException() {
		super("Identificacao do empregado nao pode ser nula.");
	}
}
