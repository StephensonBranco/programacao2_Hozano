package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class SalarioNaoNumericoException extends Exception {
	public SalarioNaoNumericoException() {
		super("Salario deve ser numerico.");
	}
}
