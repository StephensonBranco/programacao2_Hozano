package br.ufal.ic.p2.bikeshare.models;

public class PessoaFisica extends Usuario {
	public PessoaFisica(String nome, String email, String senha, String tipo, String cpf, String plano, String mensalidade) {
		super(nome, email, senha, tipo, cpf, plano, mensalidade);
	}
}
