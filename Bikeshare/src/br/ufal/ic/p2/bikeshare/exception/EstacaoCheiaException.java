package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class EstacaoCheiaException extends Exception {
	public EstacaoCheiaException() {
		super("Estacao cheia.");
	}
}
