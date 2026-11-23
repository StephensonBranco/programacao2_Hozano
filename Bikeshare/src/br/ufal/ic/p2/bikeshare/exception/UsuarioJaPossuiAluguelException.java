package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class UsuarioJaPossuiAluguelException extends Exception {
	public UsuarioJaPossuiAluguelException() {
		super("Usuario ja possui um aluguel em andamento.");
	}
}
