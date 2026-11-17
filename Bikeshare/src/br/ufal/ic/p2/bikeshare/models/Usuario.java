package br.ufal.ic.p2.bikeshare.models;

public class Usuario {
	private String nome;
	private String email;
	private String senha;
	private String tipo;
	private String cadastro;
	private String estado;
	private String plano;
	private String metodoPagamento;
	
	public Usuario(String nome, String email, String senha, String tipo, String cadastro, String estado, String plano) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
		this.cadastro = cadastro;
		this.estado = estado;
		this.plano = plano;
		this.metodoPagamento = "nenhum";
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCadastro() {
		return cadastro;
	}
	public void setCadastro(String cadastro) {
		this.cadastro = cadastro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}
}
