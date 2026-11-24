package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class DevolucaoAntesException extends Exception {
	public DevolucaoAntesException() {
		super("Data de devolucao anterior a data de retirada.");
	}
}
