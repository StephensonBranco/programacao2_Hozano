package br.ufal.ic.p2.bikeshare.servicos;


import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;

public class CadastroEstacoes {
	private final RepositorioEstacoes repoEstacoes;
	
	public CadastroEstacoes(RepositorioEstacoes repoEstacoes) {
		this.repoEstacoes = repoEstacoes;
	}
	
	public String criarEstacao(String nome, String endereco, String capacidade) throws Exception {
		Estacao estacao = new Estacao(nome, endereco, capacidade);
		return repoEstacoes.adicionar(estacao);
	}
}
