package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class CnpjNuloException extends Exception {
	public CnpjNuloException() {
		super("CNPJ nao pode ser nulo.");
	}
}
