package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class NaoHaEmpregadoNomeException extends Exception {
	public NaoHaEmpregadoNomeException() {
		super("Nao ha empregado com esse nome.");
	}
}
