package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioUsuarios;

public class CadastroAlugueis {
	private final RepositorioUsuarios repoUsuarios;
	private final RepositorioBicicletas repoBicicletas;
	private final RepositorioAlugueis repoAlugueis;
	
	public CadastroAlugueis(RepositorioUsuarios repoUsuarios, RepositorioBicicletas repoBicicletas, RepositorioAlugueis repoAlugueis) {
		this.repoUsuarios = repoUsuarios;
		this.repoBicicletas = repoBicicletas;
		this.repoAlugueis = repoAlugueis;
	}
	
	public String criarAluguel(String usuario, String estacao, String bicicleta, String data) throws Exception {
		Usuario user = repoUsuarios.get(usuario);
		Bicicleta bike = repoBicicletas.get(bicicleta);
		repoBicicletas.removerBicicletaDaEstacao(estacao, bicicleta);
		
		String id_aluguel = repoAlugueis.adicionar(new Aluguel(usuario, estacao, bicicleta, data));
		user.setAluguelAtivo(id_aluguel);
		user.setBicicleta(bicicleta);
		bike.setEstado("emUso");
		bike.setEstacao("nenhuma");
		
		return id_aluguel;
	}
}
