package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class HorasNaoPositivaException extends Exception {
	public HorasNaoPositivaException() {
		super("Horas devem ser positivas.");
	}
}
