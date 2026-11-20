package br.ufal.ic.p2.bikeshare.models;

import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.bikeshare.util.Validador;

public class Estacao {
	private String nome;
	private String endereco;
	private int capacidade;
	private Map<String, Bicicleta> bicicletas = new LinkedHashMap<>();
	
	public Estacao() {
		
	}
	
	public Estacao(String nome, String endereco, String capacidade) throws Exception {
		setNome(nome);
		setEndereco(endereco);
		setCapacidade(capacidade);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws Exception {
		Validador.nome(nome);
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) throws Exception {
		Validador.endereco(endereco);
		this.endereco = endereco;
	}

	public int getCapacidade() {
		return capacidade;
	}

	//XML
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	
	public void setCapacidade(String capacidade) throws Exception {
		Validador.capacidade(capacidade);
		this.capacidade = Integer.parseInt(capacidade);
	}

	public Map<String, Bicicleta> getBicicletas() {
		return new LinkedHashMap<String, Bicicleta>(this.bicicletas);
	}

	//Usado no XML
	public void setBicicletas(Map<String, Bicicleta> bicicletas) {
		this.bicicletas = bicicletas;
	}
}
