package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.exception.BateriaInsuficienteException;
import br.ufal.ic.p2.bikeshare.exception.MetodoPagamentoNaoCadastradoException;
import br.ufal.ic.p2.bikeshare.exception.SaldoInsuficienteException;
import br.ufal.ic.p2.bikeshare.exception.UsuarioJaPossuiAluguelException;
import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Eletrica;
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
		Aluguel rent = new Aluguel(usuario, estacao, bicicleta, data);
		
		repoBicicletas.removerBicicletaDaEstacao(estacao, bicicleta);
		if("eletrica".equals(bike.getTipo()) && ((Eletrica)bike).getNivelBateria() < 15)
			throw new BateriaInsuficienteException();
		if("nenhum".equals(user.getMetodoPagamento()))
			throw new MetodoPagamentoNaoCadastradoException();
		if("saldoPreCarregado".equals(user.getMetodoPagamento()) && user.getSaldo() == 0)
			throw new SaldoInsuficienteException();
		if(user.getAluguelAtivo() != null & !"nenhum".equals(user.getAluguelAtivo()))
			throw new UsuarioJaPossuiAluguelException();
		
		String id_aluguel = repoAlugueis.adicionar(rent);
		user.setAluguelAtivo(id_aluguel);
		user.setBicicleta(bicicleta);
		bike.setEstacao("nenhuma");
		bike.setEstado("emUso");
		
		return id_aluguel;
	}
}
