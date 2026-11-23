package br.ufal.ic.p2.bikeshare.repositorio;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.bikeshare.exception.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class RepositorioEstacoes implements Repositorio<Estacao> {
	private static final File ARQUIVO_ESTACOES = new File("/XML/estacoes.xml");
	private static final File ARQUIVO_ID_ESTACOES = new File("XML/id_estacoes.xml");

	private Map<String, Estacao> estacoes;
	private int id_atual_estacoes;

	public RepositorioEstacoes() {
		estacoes = FRepo.carregar(ARQUIVO_ESTACOES, new LinkedHashMap<>());
		id_atual_estacoes = FRepo.carregar(ARQUIVO_ID_ESTACOES, 1);
	}
	
	
	@Override
	public void zerar() {
		estacoes.clear();
		id_atual_estacoes = 1;
	}

	@Override
	public void salvar() {
		FRepo.gravar(ARQUIVO_ESTACOES, estacoes);
		FRepo.gravar(ARQUIVO_ID_ESTACOES, id_atual_estacoes);
	}
	
	@Override
	public void remover(String estacao) throws Exception {
		Validador.idEstacao(estacao);
		if(!estacao.contains(estacao))
			throw new EstacaoNaoExisteException();
		estacoes.remove(estacao);
	}

	@Override
	public String adicionar(Estacao estacao) throws Exception {
		return FRepo.adicionar(estacoes, "E", estacao, id_atual_estacoes++);
	}

	@Override
	public Estacao get(String estacao) throws Exception {
		Validador.idEstacao(estacao);
		return FRepo.get(estacoes, estacao, EstacaoNaoExisteException::new);
	}

	@Override
	public Map<String, Estacao> getLista() {
		return new LinkedHashMap<String, Estacao>(estacoes);
	}
	
}
