package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class EnderecoNuloException extends Exception {
	public EnderecoNuloException() {
		super("Endereco nao pode ser nulo.");
	}
}
