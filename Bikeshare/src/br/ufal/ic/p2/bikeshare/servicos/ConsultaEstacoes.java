package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.exception.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Eletrica;
import br.ufal.ic.p2.bikeshare.util.Validador;

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
		
		int q = 0;
		
		for(String id : station.getBicicletas()) {
			if(repo.getBicicleta(id).estaDisponivel())
				q++;
		}
		
		return String.valueOf(q);
	}
	
	public String getAtributoBicicleta(String bicicleta, String atributo) throws Exception {
		Bicicleta bike = repo.getBicicleta(bicicleta);
		return switch (atributo) {
			case "tipo" -> bike.getTipo();
			case "estado" -> bike.getEstado();
			case "estacao" -> bike.getEstacao();
			case "nivelBateria" -> {
				Validador.eletrica(bike);
				yield String.valueOf(((Eletrica)bike).getNivelBateria());
			}
			default -> throw new AtributoNaoExisteException();
		};
	}
}
