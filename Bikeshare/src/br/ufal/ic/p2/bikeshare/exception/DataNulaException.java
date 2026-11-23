package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class DataNulaException extends Exception {
	public DataNulaException() {
		super("Data nao pode ser nula.");
	}
}
