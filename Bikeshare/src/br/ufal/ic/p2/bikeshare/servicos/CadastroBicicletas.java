package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.exception.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.exception.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Convencional;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Eletrica;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;

public class CadastroBicicletas {
	private final RepositorioBicicletas repoBicicletas;
	
	public CadastroBicicletas(RepositorioBicicletas repoBicicletas) {
		this.repoBicicletas = repoBicicletas;
	}
	
	//id é o nome da bicicleta "bc1, be1..."
	public void criarBicicleta(String estacao, String id, String tipo, String nivelBateria) throws Exception {
		Bicicleta bicicleta;
		switch (tipo) {
			case "convencional" -> {
				if(nivelBateria != null)
					throw new BicicletaNaoPossuiBateriaException();
				bicicleta = new Convencional(tipo, estacao);
			}
			case "eletrica" -> bicicleta = new Eletrica(tipo, nivelBateria, estacao);
			default -> throw new TipoInvalidoException();
		}
		
		repoBicicletas.adicionar(estacao, id, bicicleta);
	}
}
