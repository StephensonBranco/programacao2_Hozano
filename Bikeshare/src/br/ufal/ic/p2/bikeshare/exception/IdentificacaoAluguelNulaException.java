package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class IdentificacaoAluguelNulaException extends Exception {
	public IdentificacaoAluguelNulaException() {
		super("Identificacao do aluguel nao pode ser nula.");
	}
}
