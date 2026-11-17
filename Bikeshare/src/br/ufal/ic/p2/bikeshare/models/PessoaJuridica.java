package br.ufal.ic.p2.bikeshare.models;

public class PessoaJuridica extends Usuario {
	private String razaoSocial;
	public PessoaJuridica(String nome, String email, String senha, String tipo, String cnpj, 
			String razaoSocial, String plano, String mensalidade) {
		super(nome, email, senha, tipo, cnpj, plano, mensalidade);
		this.razaoSocial = razaoSocial;
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
}