package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class IdentificacaoEstacaoNulaException extends Exception {
	public IdentificacaoEstacaoNulaException() {
		super("Identificacao da estacao nao pode ser nula.");		
	}
}
