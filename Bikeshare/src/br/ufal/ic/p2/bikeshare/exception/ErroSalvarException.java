package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class ErroSalvarException extends RuntimeException {
	public ErroSalvarException(Throwable e) {
		super("Erro ao salvar dados", e);
	}
}
