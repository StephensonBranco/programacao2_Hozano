package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class AluguelNaoEstaEmAndamentoException extends Exception {
	public AluguelNaoEstaEmAndamentoException() {
		super("Aluguel nao esta em andamento.");
	}
}
