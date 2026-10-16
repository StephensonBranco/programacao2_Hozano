package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class EmpregadoNaoEhAssalariadoException extends Exception {
	public EmpregadoNaoEhAssalariadoException() {
		super("Empregado nao eh assalariado.");
	}
}
