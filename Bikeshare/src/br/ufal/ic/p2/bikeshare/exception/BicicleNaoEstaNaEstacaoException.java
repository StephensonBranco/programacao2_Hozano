package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class BicicleNaoEstaNaEstacaoException extends Exception {
	public BicicleNaoEstaNaEstacaoException() {
		super("Bicicleta nao esta nesta estacao.");
	}
}
