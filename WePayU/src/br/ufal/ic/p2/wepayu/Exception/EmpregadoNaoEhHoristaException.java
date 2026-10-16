package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class EmpregadoNaoEhHoristaException extends Exception {
	public EmpregadoNaoEhHoristaException() {
		super("Empregado nao eh horista.");
	}
}
