package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.exception.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Eletrica;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class ConsultaBicicletas {
	private final RepositorioBicicletas repoBicicletas;
	
	public ConsultaBicicletas(RepositorioBicicletas repoBicicletas) {
		this.repoBicicletas = repoBicicletas;
	}
	
	public String getAtributo(String bicicleta, String atributo) throws Exception {
		Bicicleta bike = repoBicicletas.get(bicicleta);
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
