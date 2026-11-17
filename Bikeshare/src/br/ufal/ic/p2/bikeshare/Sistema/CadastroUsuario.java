package br.ufal.ic.p2.bikeshare.Sistema;

import br.ufal.ic.p2.bikeshare.exception.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.models.PessoaFisica;
import br.ufal.ic.p2.bikeshare.models.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.Usuario;
import br.ufal.ic.p2.bikeshare.util.Formatador;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class CadastroUsuario {
	private final Repositorio repo;
	public CadastroUsuario(Repositorio repo) {
		this.repo = repo;
	}
	
	public String criarUsuario(String nome, String email, String senha, String tipo, String cadastro, String estado,
								String razaoSocial, String plano, String mensalidade, String metodoPagamento) throws Exception{
		Usuario usuario;
		Double mensal = null;
		
		//Valores padrões
		if(plano==null)
			plano = "avulso";
		if(estado==null)
			estado = "ativo";
		if(metodoPagamento==null)
			metodoPagamento = "nenhum";
		
		//Validação geral 
		Validador.criacao(repo.getListaUsuarios(), nome, email, senha, tipo, cadastro, estado, razaoSocial, plano, mensalidade, metodoPagamento);
		
		if(plano.equals("assinante"))
			mensal = Formatador.stringToDouble(mensalidade);
		else
			mensal = null;
		
		
		switch (tipo) {
			case "pessoaFisica":
				usuario = new PessoaFisica(nome, email, senha, tipo, cadastro, estado, plano, mensal, metodoPagamento);
				break;
			case "pessoaJuridica":
				usuario = new PessoaJuridica(nome, email, senha, tipo, cadastro, estado, razaoSocial, plano, mensal, metodoPagamento);
				break;
			default:
				throw new TipoInvalidoException();
		}
		String id = repo.adicionar(usuario);
		return id;
	}
}
