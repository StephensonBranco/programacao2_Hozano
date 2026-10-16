package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class AtributoNaoExisteException extends Exception {
	public AtributoNaoExisteException() {
		super("Atributo nao existe.");
	}
}
