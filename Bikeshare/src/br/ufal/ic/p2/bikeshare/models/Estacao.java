package br.ufal.ic.p2.bikeshare.models;

import java.util.ArrayList;
import java.util.List;

import br.ufal.ic.p2.bikeshare.exception.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.EstacaoSemVagasException;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class Estacao {
	private String nome;
	private String endereco;
	private int capacidade;
	private List<String> bicicletas = new ArrayList<>();
	
	public Estacao() {
		
	}
	
	public Estacao(String nome, String endereco, String capacidade) throws Exception {
		setNome(nome);
		setEndereco(endereco);
		setCapacidade(capacidade);
	}
	
	public void removerBicicleta(String bicicleta) throws BicicletaNaoExisteException {
		bicicletas.remove(bicicleta);
	}
	
	public void adicionarBicicleta(String bicicleta) throws EstacaoSemVagasException {
		if(capacidade==bicicletas.size())
			throw new EstacaoSemVagasException();
		bicicletas.add(bicicleta);
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

	public List<String> getBicicletas() {
		return new ArrayList<String>(this.bicicletas);
	}

	public void setBicicletas(List<String> bicicletas) {
		this.bicicletas = bicicletas;
	}
}
