package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.util.Validador;

public class PessoaJuridica extends Usuario {
	private String razaoSocial;
	
	public PessoaJuridica() {
		
	}
	
	public PessoaJuridica(String nome, String email, String senha, String tipo, String cnpj,
			String razaoSocial, String plano, String mensalidade) throws Exception{
		super(nome, email, senha, tipo, cnpj, plano, mensalidade);
		setCadastro(cnpj);
		setRazaoSocial(razaoSocial);
	}
	
	@Override
	public void setCadastro(String cnpj) throws Exception{
		Validador.cnpj(cnpj);
		this.cadastro = cnpj;
	}
	
	@Override
	public void setRazaoSocial(String razaoSocial) throws Exception {
		Validador.razaoSocial(razaoSocial);
		this.razaoSocial = razaoSocial;
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
}