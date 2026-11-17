package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class AtributoNaoExisteException extends Exception {
	public AtributoNaoExisteException() {
		super("Atributo nao existe.");
	}
}
