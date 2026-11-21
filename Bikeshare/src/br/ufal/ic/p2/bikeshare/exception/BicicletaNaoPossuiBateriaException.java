package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class BicicletaNaoPossuiBateriaException extends Exception {
	public BicicletaNaoPossuiBateriaException() {
		super("Bicicleta nao possui bateria.");
	}
}
