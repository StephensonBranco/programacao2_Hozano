package br.ufal.ic.p2.bikeshare.models;

public class PessoaJuridica extends Usuario {
	private String razaoSocial;
	public PessoaJuridica(String nome, String email, String senha, String tipo, String cnpj, String estado,
			String razaoSocial, String plano, Double mensalidade, String metodoPagamento) {
		super(nome, email, senha, tipo, cnpj, estado, plano, mensalidade, metodoPagamento);
		this.razaoSocial = razaoSocial;
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
}