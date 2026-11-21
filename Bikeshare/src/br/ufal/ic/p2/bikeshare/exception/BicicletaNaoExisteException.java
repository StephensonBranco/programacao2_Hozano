package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class BicicletaNaoExisteException extends Exception {
	public BicicletaNaoExisteException() {
		super("Bicicleta nao existe.");
	}
}
