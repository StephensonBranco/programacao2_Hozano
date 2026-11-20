package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.util.Validador;

public class Usuario {
	private String nome;
	private String email;
	private String senha;
	private String tipo;
	private String cadastro;
	private String estado;
	private String plano;
	private Double mensalidade;
	private String metodoPagamento;
	private String aluguelAtivo;
	private Double totalPendencias;
	private Cartao cartao;
	private String chavePix;
	private Double saldo;
	
	public Usuario() {
		
	}
	
	public Usuario(String nome, String email, String senha, String tipo, String cadastro, String plano, Double mensalidade) {
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setTipo(tipo);
		setCadastro(cadastro);
		setEstado("ativo");
		setPlano(plano);
		setMensalidade(mensalidade);
		setMetodoPagamento("nenhum");
		setAluguelAtivo("nenhum");
		setTotalPendencias(0.00);
		setCartao(null);
		setChavePix(null);
		setSaldo(null);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) throws Exception {
		Validador.nome(nome);
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws Exception {
		Validador.email(email);
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) throws Exception {
		Validador.email(email);
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
	
	//CPF E CNPJ NAS SUBCLASSES
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

	public Double getMensalidade() {
		return mensalidade;
	}

	public void setMensalidade(Double mensalidade) {
		this.mensalidade = mensalidade;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public String getAluguelAtivo() {
		return aluguelAtivo;
	}

	public void setAluguelAtivo(String aluguelAtivo) {
		this.aluguelAtivo = aluguelAtivo;
	}

	public Double getTotalPendencias() {
		return totalPendencias;
	}

	public void setTotalPendencias(Double totalPendencias) {
		this.totalPendencias = totalPendencias;
	}

	public Cartao getCartao() {
		return new Cartao(this.cartao);
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
}
