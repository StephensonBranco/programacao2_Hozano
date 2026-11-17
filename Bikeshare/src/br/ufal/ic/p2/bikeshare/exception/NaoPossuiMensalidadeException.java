package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NaoPossuiMensalidadeException extends Exception {
	public NaoPossuiMensalidadeException() {
		super("Plano nao possui mensalidade.");
	}
}
