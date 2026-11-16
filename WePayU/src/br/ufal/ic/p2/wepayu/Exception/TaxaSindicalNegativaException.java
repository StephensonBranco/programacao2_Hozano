package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class TaxaSindicalNegativaException extends Exception {
	public TaxaSindicalNegativaException() {
		super("Taxa sindical deve ser nao-negativa.");
	}
}
