package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class EmpregadoNaoEhSindicalizadoException extends Exception {
	public EmpregadoNaoEhSindicalizadoException() {
		super("Empregado nao eh sindicalizado.");
	}
}
