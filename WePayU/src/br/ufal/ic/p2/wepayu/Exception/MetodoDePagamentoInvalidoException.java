package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class MetodoDePagamentoInvalidoException extends Exception {
	public MetodoDePagamentoInvalidoException() {
		super("Metodo de pagamento invalido.");
	}
}
