package br.ufal.ic.p2.wepayu.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Horista extends Empregado {
	private Map<String, Double> cartaoDePonto = new LinkedHashMap<String, Double>();
	
	public Horista() {}
	
	public Horista(String nome, String endereco, String tipo, double salario) {
		super(nome, endereco, tipo, salario);
	}
	
	public Map<String, Double> getCartaoDePonto() {
		return cartaoDePonto;
	}

	public void setCartaoDePonto(Map<String, Double> cartaoDePonto) {
		this.cartaoDePonto = cartaoDePonto;
	}
	
}
