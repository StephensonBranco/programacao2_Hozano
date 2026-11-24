package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class BateriaInsuficienteException extends Exception {
	public BateriaInsuficienteException() {
		super("Nivel de bateria insuficiente.");		
	}
}
