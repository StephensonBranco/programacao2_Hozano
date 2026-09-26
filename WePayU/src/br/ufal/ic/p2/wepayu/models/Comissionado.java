package br.ufal.ic.p2.wepayu.models;

public class Comissionado extends Empregado {
	private double taxaDeComissao;
	
	public Comissionado(String nome, String endereco, String tipo, double salario, double taxaDeComissao) {
		super(nome, endereco, tipo, salario);
		this.taxaDeComissao = taxaDeComissao;
	}
	
	public double getTaxaDeComissao() {
		return taxaDeComissao;
	}
}
