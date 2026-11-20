package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.models.Estacao;

public class CadastroEstacoes {
	private final Repositorio repo;
	
	public CadastroEstacoes(Repositorio repo) {
		this.repo = repo;
	}
	
	public String criarEstacao(String nome, String endereco, String capacidade) throws Exception {
		Estacao estacao = new Estacao(nome, endereco, capacidade);
		return repo.adicionarEstacao(estacao);
	}
}
