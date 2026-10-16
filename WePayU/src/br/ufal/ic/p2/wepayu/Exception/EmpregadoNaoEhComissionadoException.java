package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class EmpregadoNaoEhComissionadoException extends Exception {
	public EmpregadoNaoEhComissionadoException() {
		super("Empregado nao eh comissionado.");
	}
}
