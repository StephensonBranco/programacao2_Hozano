package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NivelBateriaNuloException extends Exception {
	public NivelBateriaNuloException() {
		super("Nivel de bateria nao pode ser nulo.");
	}
}
