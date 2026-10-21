package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class AgendaNaoDisponivelException extends Exception {
	public AgendaNaoDisponivelException() {
		super("Agenda de pagamento nao esta disponivel");
	}
}
