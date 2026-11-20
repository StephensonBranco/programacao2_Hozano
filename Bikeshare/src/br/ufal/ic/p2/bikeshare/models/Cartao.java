package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.util.Validador;

public class Cartao {
	private String numeroCartao;
	private String validadeCartao;
	
	public Cartao() {
		
	}
	
	public Cartao(String numeroCartao, String validadeCartao) throws Exception{
		setNumeroCartao(numeroCartao);
		setValidadeCartao(validadeCartao);
	}
	
	public Cartao(Cartao cartao) {
		if(cartao==null) {
			this.numeroCartao = null;
			this.validadeCartao = null;
		}
		else {
			this.numeroCartao = cartao.getNumeroCartao();
			this.validadeCartao = cartao.getValidadeCartao();			
		}
	}

	public String getValidadeCartao() {
		return validadeCartao;
	}

	public void setValidadeCartao(String validadeCartao) throws Exception {
		Validador.validadeCartao(validadeCartao);
		this.validadeCartao = validadeCartao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) throws Exception {
		Validador.numeroCartao(numeroCartao);
		this.numeroCartao = numeroCartao;
	}
	
	
}
