package br.ufal.ic.p2.bikeshare.models.bicicleta;


public abstract class Bicicleta {
	private String tipo;
	protected String estado;
	private String estacao;
	
	public Bicicleta() {
		
	}
	
	public Bicicleta(String tipo, String estacao) {
		setTipo(tipo);
		setEstado("disponivel");
		setEstacao(estacao);
	}
	
	public abstract boolean estaDisponivel();

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		
		this.estado = estado;
	}

	public String getEstacao() {
		return estacao;
	}

	public void setEstacao(String estacao) {
		this.estacao = estacao;
	}
}
