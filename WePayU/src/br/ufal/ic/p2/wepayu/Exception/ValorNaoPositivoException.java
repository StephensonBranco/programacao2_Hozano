package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class ValorNaoPositivoException extends Exception {
	public ValorNaoPositivoException() {
		super("Valor deve ser positivo.");
	}
}
