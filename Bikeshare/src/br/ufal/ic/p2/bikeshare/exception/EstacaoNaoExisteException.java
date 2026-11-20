package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class EstacaoNaoExisteException extends Exception {
	public EstacaoNaoExisteException() {
		super("Estacao nao existe.");
	}
}
