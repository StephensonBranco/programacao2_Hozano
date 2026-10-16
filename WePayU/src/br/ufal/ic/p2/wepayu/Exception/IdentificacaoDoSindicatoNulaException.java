package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class IdentificacaoDoSindicatoNulaException extends Exception {
	public IdentificacaoDoSindicatoNulaException() {
		super("Identificacao do sindicato nao pode ser nula.");
	}
}
