package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class DesfazerException extends Exception {
	public DesfazerException() {
		super("Nao ha comando a desfazer.");
	}
}
