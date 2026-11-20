package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NumeroCartaoNuloException extends Exception {
	public NumeroCartaoNuloException() {
		super("Numero do cartao nao pode ser nulo.");
	}
}
