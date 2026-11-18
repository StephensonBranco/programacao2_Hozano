package br.ufal.ic.p2.bikeshare.models;

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
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
		this.cadastro = cadastro;
		this.estado = "ativo";
		this.plano = plano;
		this.mensalidade = mensalidade;
		this.metodoPagamento = "nenhum";
		this.aluguelAtivo = "nenhum";
		this.totalPendencias = 0.00;
		this.cartao = null;
		this.chavePix = null;
		this.saldo = null;
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
