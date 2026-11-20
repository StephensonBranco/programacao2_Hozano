package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class SaldoNuloException extends Exception {
	public SaldoNuloException() {
		super("Valor nao pode ser nulo.");
	}
}
