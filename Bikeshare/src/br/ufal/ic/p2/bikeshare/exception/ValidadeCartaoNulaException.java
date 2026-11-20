package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class ValidadeCartaoNulaException extends Exception {
	public ValidadeCartaoNulaException() {
		super("Validade do cartao nao pode ser nula.");
	}
}
