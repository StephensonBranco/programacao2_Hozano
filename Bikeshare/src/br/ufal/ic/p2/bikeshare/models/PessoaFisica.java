package br.ufal.ic.p2.bikeshare.models;

public class PessoaFisica extends Usuario {
	public PessoaFisica() {
	
	}
	
	public PessoaFisica(String nome, String email, String senha, String tipo, String cpf, String plano, Double mensalidade) {
		super(nome, email, senha, tipo, cpf, plano, mensalidade);
	}
}
