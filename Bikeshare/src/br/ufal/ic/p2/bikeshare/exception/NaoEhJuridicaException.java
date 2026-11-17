package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NaoEhJuridicaException extends Exception {
	public NaoEhJuridicaException() {
		super("Usuario nao eh pessoa juridica.");
	}
}
