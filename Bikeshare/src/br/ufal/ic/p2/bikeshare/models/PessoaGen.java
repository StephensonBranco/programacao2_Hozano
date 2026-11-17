package br.ufal.ic.p2.bikeshare.models;

public class PessoaGen {
	private String nome;
	private String email;
	private String senha;
	private String tipo;
	private String id;
	public PessoaGen(String nome, String email, String senha, String tipo, String id) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
		this.id = id;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
