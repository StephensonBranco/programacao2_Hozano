package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class EmailNuloException extends Exception {
	public EmailNuloException() {
		super("Email nao pode ser nulo.");
	}
}
