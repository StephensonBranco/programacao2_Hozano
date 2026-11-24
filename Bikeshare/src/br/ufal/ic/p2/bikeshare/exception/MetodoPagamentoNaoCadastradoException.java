package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class MetodoPagamentoNaoCadastradoException extends Exception {
	public MetodoPagamentoNaoCadastradoException() {
		super("Metodo de pagamento nao cadastrado.");
	}
}
