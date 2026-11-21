package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class IdentificacaoBicicletaNulaException extends Exception {
	public IdentificacaoBicicletaNulaException() {
		super("Identificacao da bicicleta nao pode ser nula.");		
	}
}
