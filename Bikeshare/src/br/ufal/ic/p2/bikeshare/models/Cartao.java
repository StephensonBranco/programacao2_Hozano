package br.ufal.ic.p2.bikeshare.models;

public class Cartao {
	private String numeroCartao;
	private String validadeCartao;
	
	public Cartao() {
		
	}
	
	public Cartao(String numeroCartao, String validadeCartao) {
		this.numeroCartao = numeroCartao;
		this.validadeCartao = validadeCartao;
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

	public void setValidadeCartao(String validadeCartao) {
		this.validadeCartao = validadeCartao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	
	
}
