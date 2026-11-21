package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NivelBateriaForaDosLimitesException extends Exception {
	public NivelBateriaForaDosLimitesException() {
		super("Nivel de bateria deve estar entre 0 e 100.");
	}
}
