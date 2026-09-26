package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class NaoEhHoristaException extends Exception {
	public NaoEhHoristaException() {
		super("Empregado nao eh horista.");
	}
}
