package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class AluguelNaoExisteException extends Exception {
	public AluguelNaoExisteException() {
		super("Alguel nao existe.");
	}
}
