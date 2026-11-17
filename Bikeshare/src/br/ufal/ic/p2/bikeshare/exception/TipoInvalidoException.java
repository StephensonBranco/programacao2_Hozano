package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class TipoInvalidoException extends Exception {
	public TipoInvalidoException() { 
		super("Tipo invalido.");
	}
}	
