package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class PlanoInvalidoException extends Exception {
	public PlanoInvalidoException() {
		super("Plano invalido.");
	}
}
