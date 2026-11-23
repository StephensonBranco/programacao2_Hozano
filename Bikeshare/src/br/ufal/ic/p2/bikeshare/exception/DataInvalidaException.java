package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class DataInvalidaException extends Exception {
	public DataInvalidaException() {
		super("Data invalida.");
	}
}
