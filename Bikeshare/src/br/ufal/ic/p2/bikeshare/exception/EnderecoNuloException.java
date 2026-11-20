package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class EnderecoNuloException extends Exception {
	public EnderecoNuloException() {
		super("Endereco nao pode ser nulo.");
	}
}
