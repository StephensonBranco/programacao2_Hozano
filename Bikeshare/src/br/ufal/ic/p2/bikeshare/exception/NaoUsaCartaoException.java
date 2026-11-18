package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NaoUsaCartaoException extends Exception {
	public NaoUsaCartaoException() {
		super("Usuario nao usa cartao.");
	}
}
