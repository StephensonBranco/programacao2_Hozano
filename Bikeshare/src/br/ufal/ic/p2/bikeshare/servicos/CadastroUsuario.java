package br.ufal.ic.p2.bikeshare.servicos;

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
	
	public String criarUsuario(String nome, String email, String senha, String tipo, String cadastro,
								String razaoSocial, String plano, String mensalidade) throws Exception {
		
		Usuario usuario;
		Double mensal = null;
		
		//Valores padrões
		if(plano==null)
			plano = "avulso";
		
		//Validação geral 
		Validador.criacao(repo.getListaUsuarios(), nome, email, senha, tipo, cadastro, razaoSocial, plano, mensalidade);
		
		if(plano.equals("assinante"))
			mensal = Formatador.stringToDouble(mensalidade);
		else
			mensal = null;
		
		
		switch (tipo) {
			case "pessoaFisica":
				usuario = new PessoaFisica(nome, email, senha, tipo, cadastro, plano, mensal);
				break;
			case "pessoaJuridica":
				usuario = new PessoaJuridica(nome, email, senha, tipo, cadastro, razaoSocial, plano, mensal);
				break;
			default:
				throw new TipoInvalidoException();
		}
		
		String id = repo.adicionar(usuario);
		return id;
	}
	
	public void alterarUsuario(String usuario, String atributo, String valor) throws Exception {
		Usuario user = repo.getUsuario(usuario);
		switch (atributo) {
			case "nome" -> user.setNome(valor);
		
		}
	}
}
