package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class EstacaoSemVagasException extends Exception {
	public EstacaoSemVagasException() {
		super("Estacao sem vagas disponiveis.");
	}
}
