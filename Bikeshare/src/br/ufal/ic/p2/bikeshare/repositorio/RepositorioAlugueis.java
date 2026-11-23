package br.ufal.ic.p2.bikeshare.repositorio;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.bikeshare.exception.AluguelNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class RepositorioAlugueis implements Repositorio<Aluguel>{
	private static final File ARQUIVO_ALUGUEIS = new File("/XML/algueis.xml");
	private static final File ARQUIVO_ID_ALUGUEIS = new File("/XML/id_algueis.xml");
	
	private final RepositorioUsuarios repoUsuarios;
	private final RepositorioBicicletas repoBicicletas;
	private Map<String, Aluguel> alugueis;
	private int id_atual_alugueis;
	
	public RepositorioAlugueis(RepositorioUsuarios repoUsuarios, RepositorioBicicletas repoBicicletas) {
		this.repoUsuarios = repoUsuarios;
		this.repoBicicletas = repoBicicletas;
		alugueis = FRepo.carregar(ARQUIVO_ALUGUEIS, new LinkedHashMap<>());
		id_atual_alugueis = FRepo.carregar(ARQUIVO_ID_ALUGUEIS, 1); 
	}
	
	@Override
	public void zerar() {
		alugueis.clear();
		id_atual_alugueis = 1;
	}

	@Override
	public void salvar() {
		FRepo.gravar(ARQUIVO_ALUGUEIS, alugueis);
		FRepo.gravar(ARQUIVO_ID_ALUGUEIS, id_atual_alugueis);
		
	}

	@Override
	public void remover(String aluguel) throws Exception {
		Validador.idAluguel(aluguel);
		if(!alugueis.containsKey(aluguel))
			throw new AluguelNaoExisteException();
		alugueis.remove(aluguel);
	}

	@Override
	public String adicionar(Aluguel aluguel) throws Exception {
		return FRepo.adicionar(alugueis, "A", aluguel, id_atual_alugueis++);
	}

	@Override
	public Aluguel get(String aluguel) throws Exception {
		Validador.idAluguel(aluguel);
		return FRepo.get(alugueis, aluguel, AluguelNaoExisteException::new);
	}

	@Override
	public Map<String, Aluguel> getLista() {
		return new LinkedHashMap<String, Aluguel>(alugueis);
	}

}
