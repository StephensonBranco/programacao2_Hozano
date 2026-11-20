package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class CapacidadeNegativaException extends Exception {
	public CapacidadeNegativaException() {
		super("Capacidade deve ser positiva.");
	}
}
