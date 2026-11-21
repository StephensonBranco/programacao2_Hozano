package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.exception.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.exception.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Convencional;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Eletrica;

public class CadastroEstacoes {
	private final Repositorio repo;
	
	public CadastroEstacoes(Repositorio repo) {
		this.repo = repo;
	}
	
	public String criarEstacao(String nome, String endereco, String capacidade) throws Exception {
		Estacao estacao = new Estacao(nome, endereco, capacidade);
		return repo.adicionarEstacao(estacao);
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
		
		repo.adicionarBicicleta(estacao, id, bicicleta);
	}
}
