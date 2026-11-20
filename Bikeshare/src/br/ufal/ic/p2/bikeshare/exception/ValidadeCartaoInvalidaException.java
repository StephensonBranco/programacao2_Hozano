package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class ValidadeCartaoInvalidaException extends Exception {
	public ValidadeCartaoInvalidaException() {
		super("Validade do cartao invalida.");
	}
}
