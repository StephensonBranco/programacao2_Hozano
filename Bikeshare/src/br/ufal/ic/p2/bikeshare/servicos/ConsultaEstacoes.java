package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.exception.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Eletrica;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.util.Formatador;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class ConsultaEstacoes {
	private final RepositorioEstacoes repoEstacoes;
	private final RepositorioBicicletas repoBicicletas;
	
	public ConsultaEstacoes(RepositorioEstacoes repoEstacoes, RepositorioBicicletas repoBicicletas) {
		this.repoEstacoes = repoEstacoes;
		this.repoBicicletas = repoBicicletas;
	}
	
	//Quantas vagas estão disponíveis na estação
	public String getVagasDisponiveis(String estacao) throws Exception {
		Estacao station = repoEstacoes.get(estacao);
		return String.valueOf(station.getCapacidade() - station.getBicicletas().size());
	}
	
	//Quantas bicicletas da estação estão disponíveis para alugar
	public String getBicicletasDisponiveis(String estacao) throws Exception {
		Estacao station = repoEstacoes.get(estacao);
		
		int q = 0;
		
		for(String id : station.getBicicletas()) {
			if(repoBicicletas.get(id).estaDisponivel())
				q++;
		}
		
		return String.valueOf(q);
	}
}
