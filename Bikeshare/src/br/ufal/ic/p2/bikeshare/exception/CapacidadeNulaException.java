package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class CapacidadeNulaException extends Exception {
	public CapacidadeNulaException() {
		super("Capacidade nao pode ser nula.");
	}
}
