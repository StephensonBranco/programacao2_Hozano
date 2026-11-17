package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class EmailInvalidoException extends Exception {
	public EmailInvalidoException() {
		super("Email invalido.");
	}
}
