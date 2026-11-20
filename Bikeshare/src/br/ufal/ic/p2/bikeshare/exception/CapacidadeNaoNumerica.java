package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class CapacidadeNaoNumerica extends Exception {
	public CapacidadeNaoNumerica() {
		super("Capacidade deve ser numerica.");
	}
}
