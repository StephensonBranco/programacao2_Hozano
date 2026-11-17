package br.ufal.ic.p2.bikeshare.models;

public class PessoaFisica extends PessoaGen {
	private String razaoSocial;
	
	public PessoaFisica(String nome, String email, String senha, String tipo, String codigo, String razaoSocial) {
		super(nome, email, senha, tipo, codigo);
		this.razaoSocial = razaoSocial;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
}
