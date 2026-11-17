package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class PlanoNaoPossuiMensalidadeException extends Exception {
	public PlanoNaoPossuiMensalidadeException() {
		super("Plano nao possui mensalidade.");
	}
}
