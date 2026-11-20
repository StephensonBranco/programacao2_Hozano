package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class IdentificacaoUsuarioNulaException extends Exception {
	public IdentificacaoUsuarioNulaException() {
		super("Identificacao do usuario nao pode ser nula.");
	}
}
