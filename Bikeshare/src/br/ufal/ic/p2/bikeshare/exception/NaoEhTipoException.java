package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NaoEhTipoException extends Exception {
	public NaoEhTipoException() {
		super("Tipo invalido.");
	}
}
