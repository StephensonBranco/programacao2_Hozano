package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class CpfNuloException extends Exception {
	public CpfNuloException() {
		super("CPF nao pode ser nulo.");
	}
}
