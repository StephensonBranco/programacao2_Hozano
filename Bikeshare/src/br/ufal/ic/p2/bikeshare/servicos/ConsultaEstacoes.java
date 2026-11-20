package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.models.Estacao;

public class ConsultaEstacoes {
	private final Repositorio repo;
	
	public ConsultaEstacoes(Repositorio repo) {
		this.repo = repo;
	}
	
	public String getVagasDisponiveis(String estacao) throws Exception {
		Estacao station = repo.getEstacao(estacao);
		return String.valueOf(station.getCapacidade() - station.getBicicletas().size());
	}
	
	public String getBicicletasDisponiveis(String estacao) throws Exception {
		Estacao station = repo.getEstacao(estacao);
		return String.valueOf(station.getBicicletas().size());
	}
}
