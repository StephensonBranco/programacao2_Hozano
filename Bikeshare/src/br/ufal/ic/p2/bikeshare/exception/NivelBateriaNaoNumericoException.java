package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NivelBateriaNaoNumericoException extends Exception {
	public NivelBateriaNaoNumericoException() {
		super("Nivel de bateria deve ser numerico.");
	}
}
