package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NaoUsaPixException extends Exception {
	public NaoUsaPixException() {
		super("Usuario nao usa pix.");
	}
}
