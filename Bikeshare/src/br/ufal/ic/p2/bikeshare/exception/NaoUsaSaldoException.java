package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class NaoUsaSaldoException extends Exception {
	public NaoUsaSaldoException() {
		super("Usuario nao usa saldo pre-carregado.");
	}
}
