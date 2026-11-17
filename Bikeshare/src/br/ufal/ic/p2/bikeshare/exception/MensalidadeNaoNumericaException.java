package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class MensalidadeNaoNumericaException extends Exception {
	public MensalidadeNaoNumericaException() {
		super("Mensalidade deve ser numerica.");
	}
}
