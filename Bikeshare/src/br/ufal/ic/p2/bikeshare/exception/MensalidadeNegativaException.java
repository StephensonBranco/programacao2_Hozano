package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class MensalidadeNegativaException extends Exception {
	public MensalidadeNegativaException() {
		super("Mensalidade deve ser nao-negativa.");
	}
}
