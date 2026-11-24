package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class UsuarioComAluguelAtivoException extends Exception {
	public UsuarioComAluguelAtivoException() {
		super("Nao e possivel remover usuario com aluguel em andamento.");
	}
}
