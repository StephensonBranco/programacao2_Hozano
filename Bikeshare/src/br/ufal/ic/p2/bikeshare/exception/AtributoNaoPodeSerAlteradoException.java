package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class AtributoNaoPodeSerAlteradoException extends Exception {
	public AtributoNaoPodeSerAlteradoException() {
		super("Atributo nao pode ser alterado.");
	}
}
