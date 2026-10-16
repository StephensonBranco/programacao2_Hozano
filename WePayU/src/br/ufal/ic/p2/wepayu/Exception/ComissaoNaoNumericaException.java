package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class ComissaoNaoNumericaException extends Exception {
	public ComissaoNaoNumericaException() {
		super("Comissao deve ser numerica.");
	}
}
