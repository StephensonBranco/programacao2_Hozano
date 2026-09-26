package br.ufal.ic.p2.wepayu.models;

public class Comissionado extends Empregado {
	private double comissao;
	
	public Comissionado() {}
	
	public Comissionado(String nome, String endereco, String tipo, double salario, double comissao) {
		super(nome, endereco, tipo, salario);
		this.comissao = comissao;
	}
	
	public double getComissao() {
		return comissao;
	}
	
	public void setComissao(double comissao) {
		this.comissao = comissao;
	}
}
