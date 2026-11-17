package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class MensalidadeNulaException extends Exception {
	public MensalidadeNulaException() {
		super("Mensalidade nao pode ser nula.");
	}
}
