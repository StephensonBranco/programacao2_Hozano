package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class NaoRecebeBancoException extends Exception {
	public NaoRecebeBancoException() {
		super("Empregado nao recebe em banco.");
	}
}
