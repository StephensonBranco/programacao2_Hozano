package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class AluguelEmAndamentoException extends Exception {
	public AluguelEmAndamentoException() {
		super("Aluguel ainda esta em andamento.");		
	}
}
