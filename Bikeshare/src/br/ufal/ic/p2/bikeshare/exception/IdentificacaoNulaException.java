package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class IdentificacaoNulaException extends Exception {
	public IdentificacaoNulaException() {
		super("Identificacao do usuario nao pode ser nula.");
	}
}
