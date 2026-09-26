package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class DataInvalidaException extends Exception {
	public DataInvalidaException(String data) {
		super("Data " + data + "invalida.");
	}
}
