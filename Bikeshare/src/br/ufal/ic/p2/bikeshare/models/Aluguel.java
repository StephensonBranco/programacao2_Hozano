package br.ufal.ic.p2.bikeshare.models;

import java.time.LocalDateTime;

import br.ufal.ic.p2.bikeshare.exception.AluguelEmAndamentoException;
import br.ufal.ic.p2.bikeshare.util.Formatador;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class Aluguel {
	private String usuario;
	private String estacaoOrigem;
	private String estacaoDestino;
	private String bicicleta;
	private LocalDateTime inicio;
	private LocalDateTime fim;
	private String estado;
	private Double valorCobrado;
	
	public Aluguel() {
		
	}
	
	public Aluguel(String usuario, String estacaoOrigem, String bicicleta, String inicio) throws Exception {
		setUsuario(usuario);
		setEstacaoOrigem(estacaoOrigem);
		setBicicleta(bicicleta);
		setInicio(inicio);
		setEstado("emAndamento");
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) throws Exception {
		Validador.idUsuario(usuario);
		this.usuario = usuario;
	}

	public String getEstacaoOrigem() {
		return estacaoOrigem;
	}

	public void setEstacaoOrigem(String estacaoOrigem) throws Exception {
		Validador.idEstacao(estacaoOrigem);
		this.estacaoOrigem = estacaoOrigem;
	}

	public String getBicicleta() {
		return bicicleta;
	}

	public void setBicicleta(String bicicleta) throws Exception {
		Validador.idBicicleta(bicicleta);
		this.bicicleta = bicicleta;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) throws Exception {
		Validador.data(inicio);
		this.inicio = Formatador.stringToDate(inicio);
	}
	
	//XML
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstacaoDestino() throws AluguelEmAndamentoException {
		if("emAndamento".equals(estado))
			throw new AluguelEmAndamentoException();
		return estacaoDestino;
	}

	public void setEstacaoDestino(String estacaoDestino) {
		this.estacaoDestino = estacaoDestino;
	}

	public LocalDateTime getFim() throws AluguelEmAndamentoException {
		if("emAndamento".equals(estado))
			throw new AluguelEmAndamentoException();
		return fim;
	}

	public void setFim(String fim) throws Exception {
		Validador.data(fim);
		this.fim = Formatador.stringToDate(fim);
	}
	
	//XML
	public void setFim(LocalDateTime fim) {
		this.fim = fim;
	}

	public Double getValorCobrado() throws AluguelEmAndamentoException {
		if("emAndamento".equals(estado))
			throw new AluguelEmAndamentoException();
		return valorCobrado;
	}

	public void setValorCobrado(String valorCobrado) {
		this.valorCobrado = Formatador.stringToDouble(valorCobrado);
	}
	
	//XML
	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
	}
}
