package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class ValorPositivoException extends Exception {
	public ValorPositivoException() {
		super("Valor deve ser positivo.");
	}
}
