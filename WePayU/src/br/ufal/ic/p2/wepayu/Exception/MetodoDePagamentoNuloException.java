package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class MetodoDePagamentoNuloException extends Exception {
	public MetodoDePagamentoNuloException() {
		super("Metodo de pagamento nao pode ser nulo.");
	}
}
