package br.ufal.ic.p2.bikeshare.Sistema;

import br.ufal.ic.p2.bikeshare.exception.NaoEhTipoException;
import br.ufal.ic.p2.bikeshare.models.PessoaFisica;
import br.ufal.ic.p2.bikeshare.models.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.Usuario;

public class CadastroUsuario {
	private final Repositorio repo;
	public CadastroUsuario(Repositorio repo) {
		this.repo = repo;
	}
	
	public String criarUsuario(String nome, String email, String senha, String tipo, String cadastro,
            String razaoSocial, String plano, String mensalidade) throws Exception{
		Usuario usuario;
		switch (tipo) {
			case "pessoaFisica":
				if(plano == null)
					usuario = new PessoaFisica(nome, email, senha, tipo, cadastro, "ativo", "avulso");
				else
					usuario = new PessoaFisica(nome, email, senha, tipo, cadastro, plano, mensalidade);
				break;
			case "pessoaJuridica":
				if(plano == null)
					usuario = new PessoaJuridica(nome, email, senha, tipo, cadastro, razaoSocial, "ativo", "avulso");
				else
					usuario = new PessoaJuridica(nome, email, senha, tipo, cadastro, razaoSocial, plano, mensalidade);
				break;
			default:
				throw new NaoEhTipoException();
		}
		String id = repo.adicionar(usuario);
		return id;
	}
}
