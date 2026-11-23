package br.ufal.ic.p2.bikeshare.repositorio;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.bikeshare.exception.BicicletaJaExisteException;
import br.ufal.ic.p2.bikeshare.exception.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.util.Validador;

// ESSE REPOSITORIO É UMA EXCESSÃO AOS OUTROS, MUITAS FUNÇÕES SÃO DIFERENTES E DEPENDEM DO ESTAÇÕES
public class RepositorioBicicletas{
	private static final File ARQUIVO_BICICLETAS = new File("/XML/bicicletas.xml");

	private Map<String, Bicicleta> bicicletas;
	private final RepositorioEstacoes repoEstacoes;

	public RepositorioBicicletas(RepositorioEstacoes repoEstacoes) {
		bicicletas = FRepo.carregar(ARQUIVO_BICICLETAS, new LinkedHashMap<>());
		this.repoEstacoes = repoEstacoes;
	}
	
	public void zerar() {
		bicicletas.clear();
	}
	
	public void salvar() {
		FRepo.gravar(ARQUIVO_BICICLETAS, bicicletas);
	}
	
	public Bicicleta get(String bicicleta) throws Exception{
		Validador.idBicicleta(bicicleta);
		return FRepo.get(bicicletas, bicicleta, BicicletaNaoExisteException::new);
	}
	
	public void removerBicicletaDaEstacao(String estacao, String bicicleta) throws Exception {
		Map<String, Estacao> estacoes = repoEstacoes.getLista();
		FRepo.verificarBicicletaEstacao(estacoes, bicicletas, estacao, bicicleta);
		estacoes.get(estacao).removerBicicleta(bicicleta);
	}
	
	public void remover(String estacao, String bicicleta) throws Exception {
		removerBicicletaDaEstacao(estacao, bicicleta);
		bicicletas.remove(bicicleta);
	}
	
	public void adicionar(String estacao, String id, Bicicleta bicicleta) throws Exception {
		Estacao station = repoEstacoes.get(estacao);
		Validador.idBicicleta(id);
		if(bicicletas.containsKey(id))
			throw new BicicletaJaExisteException();
		station.adicionarBicicleta(id);
		bicicletas.put(id, bicicleta);
	}
}
