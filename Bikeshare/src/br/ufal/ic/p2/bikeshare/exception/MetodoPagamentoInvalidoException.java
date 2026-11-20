package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class MetodoPagamentoInvalidoException extends Exception {
	public MetodoPagamentoInvalidoException() {
		super("Valor de metodo de pagamento invalido.");
	}
}
