package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class UsuarioNaoExisteException extends Exception {
	public UsuarioNaoExisteException() {
		super("Usuario nao existe.");
	}
}
