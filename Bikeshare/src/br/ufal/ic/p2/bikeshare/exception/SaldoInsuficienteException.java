package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class SaldoInsuficienteException extends Exception {
	public SaldoInsuficienteException() {
		super("Saldo insuficiente.");
	}
}
