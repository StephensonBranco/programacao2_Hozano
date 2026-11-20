package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class ChavePixNulaException extends Exception {
	public ChavePixNulaException() {
		super("Chave pix nao pode ser nula.");
	}
}
