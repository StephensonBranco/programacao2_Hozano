package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class ErroCarregarException extends RuntimeException {
	public ErroCarregarException(Throwable e) {
		super("Erro ao carregar dados", e);
	}
}
