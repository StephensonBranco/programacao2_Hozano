package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class SaldoNaoNumericoException extends Exception {
	public SaldoNaoNumericoException() {
		super("Valor deve ser numerico.");
	}
}
