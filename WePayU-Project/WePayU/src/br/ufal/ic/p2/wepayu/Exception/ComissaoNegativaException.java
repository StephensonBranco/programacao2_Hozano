package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class ComissaoNegativaException extends Exception {
	public ComissaoNegativaException() {
		super("Comissao deve ser nao-negativa.");
	}
}
