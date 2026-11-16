package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class IdentificacaoDoEmpregadoNulaException extends Exception {
	public IdentificacaoDoEmpregadoNulaException() {
		super("Identificacao do empregado nao pode ser nula.");
	}
}
