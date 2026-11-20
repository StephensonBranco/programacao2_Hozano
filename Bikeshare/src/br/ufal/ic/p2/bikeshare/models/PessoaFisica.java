package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exception.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class PessoaFisica extends Usuario {
	public PessoaFisica() {
		
	}
	
	public PessoaFisica(String nome, String email, String senha, String tipo, String cpf, String razaoSocial, String plano, String mensalidade) throws Exception {
		super(nome, email, senha, tipo, cpf, plano, mensalidade);
		setCadastro(cpf);
		setRazaoSocial(razaoSocial);
	}
	
	@Override
	public void setCadastro(String cpf) throws Exception{
		Validador.cpf(cpf);
		this.cadastro = cpf;
	}
	
	@Override
	public void setRazaoSocial(String razaoSocial) throws Exception { 
		if(razaoSocial!=null)
			throw new TipoInvalidoException();
	}
}
