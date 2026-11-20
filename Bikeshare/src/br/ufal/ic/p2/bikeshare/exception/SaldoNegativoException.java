package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class SaldoNegativoException extends Exception {
	public SaldoNegativoException() {
		super("Valor deve ser positivo.");
	}
}
