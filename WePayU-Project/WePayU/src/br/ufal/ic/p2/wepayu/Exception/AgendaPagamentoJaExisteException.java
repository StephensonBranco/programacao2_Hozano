package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class AgendaPagamentoJaExisteException extends Exception {
	public AgendaPagamentoJaExisteException() {
		super("Agenda de pagamentos ja existe");
	}
}
