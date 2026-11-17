package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NaoEhFisicaException extends Exception {
	public NaoEhFisicaException() {
		super("Usuario nao eh pessoa fisica.");
	}
}
