package br.ufal.ic.p2.bikeshare.models.usuario;

import br.ufal.ic.p2.bikeshare.exception.UsuarioJaPossuiAluguelException;
import br.ufal.ic.p2.bikeshare.models.Cartao;
import br.ufal.ic.p2.bikeshare.util.Formatador;
import br.ufal.ic.p2.bikeshare.util.Validador;

public abstract class Usuario {
	private String nome;
	private String email;
	private String senha;
	private String tipo;
	protected String cadastro;
	private String estado;
	private String plano;
	private Double mensalidade;
	private String metodoPagamento;
	private String aluguelAtivo;
	private Double totalPendencias;
	private Cartao cartao;
	private String chavePix;
	private Double saldo;
	private String bicicleta;
	
	public Usuario() {
		
	}
	
	public Usuario(String nome, String email, String senha, String tipo, String cadastro, String plano, String mensalidade) throws Exception{
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setTipo(tipo);
		setEstado("ativo");
		setPlano(plano);
		setMensalidade(mensalidade);
		setMetodoPagamento("nenhum");
		setAluguelAtivo("nenhum");
		setTotalPendencias(0.00);
		setCartao(null);
		setChavePix(null);
		setSaldo(0.00);
		setBicicleta(null);
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
		Validador.senha(senha);
		this.senha = senha;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) throws Exception {
		this.tipo = tipo;
	}
	
	public String getCadastro() {
		return cadastro;
	}
	
	public abstract void setCadastro(String cadastro) throws Exception;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) throws Exception {
		Validador.plano(plano);
		this.plano = plano;
	}

	public Double getMensalidade() {
		return this.mensalidade;
	}

	public void setMensalidade(String mensalidade) throws Exception {
		Validador.mensalidade(mensalidade, this.plano);
		this.mensalidade = Formatador.stringToDouble(mensalidade);
	}
	
	//Para o XML
	public void setMensalidade(Double mensalidade) {
		this.mensalidade = mensalidade;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) throws Exception {
		Validador.metodoPagamento(metodoPagamento);
		this.metodoPagamento = metodoPagamento;
	}

	public String getAluguelAtivo() {
		return aluguelAtivo;
	}

	public void setAluguelAtivo(String aluguelAtivo) throws UsuarioJaPossuiAluguelException {
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

	public void setChavePix(String chavePix) throws Exception {
		Validador.chavePix(chavePix);
		this.chavePix = chavePix;
	}

	public Double getSaldo() {
		return saldo;
	}

	//XML
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public void setSaldo(String saldo) throws Exception {
		Validador.saldo(saldo);
		this.saldo = Formatador.stringToDouble(saldo) + this.saldo;
	}

	public String getBicicleta() {
		return bicicleta;
	}

	public void setBicicleta(String bicicleta) {
		this.bicicleta = bicicleta;
	}
}
