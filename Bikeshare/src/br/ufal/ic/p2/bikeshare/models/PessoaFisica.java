package br.ufal.ic.p2.bikeshare.models;

public class PessoaFisica extends Usuario {
	public PessoaFisica(String nome, String email, String senha, String tipo, String cpf, String estado, String plano, Double mensalidade, String metodoPagamento) {
		super(nome, email, senha, tipo, cpf, estado, plano, mensalidade, metodoPagamento);
	}
}
