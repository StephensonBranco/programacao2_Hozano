package br.ufal.ic.p2.bikeshare.models.usuario;

import br.ufal.ic.p2.bikeshare.util.Validador;

public class PessoaFisica extends Usuario {
	public PessoaFisica() {
		
	}
	
	public PessoaFisica(String nome, String email, String senha, String tipo, String cpf, String plano, String mensalidade) throws Exception {
		super(nome, email, senha, tipo, cpf, plano, mensalidade);
		setCadastro(cpf);
	}
	
	@Override
	public void setCadastro(String cpf) throws Exception{
		Validador.cpf(cpf);
		this.cadastro = cpf;
	}
}
