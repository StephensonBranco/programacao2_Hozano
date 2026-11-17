package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NomeNuloException extends Exception {
	public NomeNuloException() {
		super("Nome nao pode ser nulo.");
	}
}
