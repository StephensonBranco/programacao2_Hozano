package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class EmailJaCadastradoException extends Exception {
	public EmailJaCadastradoException() {
		super("Email ja cadastrado.");
	}
}
