package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class TaxaSindicalNaoNumericaException extends Exception {
	public TaxaSindicalNaoNumericaException() {
		super("Taxa sindical deve ser numerica.");
	}
}
