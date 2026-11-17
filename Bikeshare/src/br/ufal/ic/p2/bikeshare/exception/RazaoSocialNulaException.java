package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class RazaoSocialNulaException extends Exception {
	public RazaoSocialNulaException() {
		super("Razao social nao pode ser nula.");
	}
}
