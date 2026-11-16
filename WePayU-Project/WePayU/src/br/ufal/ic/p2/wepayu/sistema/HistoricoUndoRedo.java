package br.ufal.ic.p2.wepayu.sistema;

import java.util.ArrayDeque;
import java.util.Deque;

import br.ufal.ic.p2.wepayu.Exception.DesfazerException;
import br.ufal.ic.p2.wepayu.Exception.RefazerException;

public class HistoricoUndoRedo {

	@FunctionalInterface
	public interface Comando {
		void executar() throws Exception;
	}

	private final RepositorioEmpregados repo;
	private final Deque<byte[]> pilhaUndo = new ArrayDeque<>();
	private final Deque<byte[]> pilhaRedo = new ArrayDeque<>();

	public HistoricoUndoRedo(RepositorioEmpregados repo) {
		this.repo = repo;
	}

	// So entra na pilha de undo se o comando nao lancar excecao
	public void executar(Comando comando) throws Exception {
		byte[] antes = repo.tirarFoto();
		comando.executar();
		registrar(antes);
	}

	// Para operacoes que nao lancam excecao (ex.: zerarSistema)
	public void executarSemExcecao(Runnable operacao) {
		byte[] antes = repo.tirarFoto();
		operacao.run();
		registrar(antes);
	}

	private void registrar(byte[] antes) {
		pilhaUndo.push(antes);
		pilhaRedo.clear();
	}

	public void desfazer() throws Exception {
		if (pilhaUndo.isEmpty())
			throw new DesfazerException();
		pilhaRedo.push(repo.tirarFoto());
		repo.restaurarFoto(pilhaUndo.pop());
	}

	public void refazer() throws Exception {
		if (pilhaRedo.isEmpty())
			throw new RefazerException();
		pilhaUndo.push(repo.tirarFoto());
		repo.restaurarFoto(pilhaRedo.pop());
	}
}