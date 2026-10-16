package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.SalarioNegativoException;
import br.ufal.ic.p2.wepayu.Exception.TaxaSindicalNegativaException;
import br.ufal.ic.p2.wepayu.util.Datas;

/*
 * Base dos empregados. Cada subclasse define o proprio tipo e as regras de pagamento
 * (quando recebe, quantos dias tem o periodo e como calcula o bruto), entao nao e preciso
 * checar tipo nem fazer cast fora dos models.
 *
 * Os getters/setters de mapa devolvem/recebem copias: o XMLEncoder/XMLDecoder precisa deles para
 * salvar e para o undo/redo, mas ninguem consegue alterar o mapa interno por eles.
 */
public abstract class Empregado {
	private String nome;
	private String endereco;
	private double salario;
	private Boolean sindicalizado = false;
	private String idSindicato;
	private Double taxaSindical;
	private Map<String, Double> taxasServico = new LinkedHashMap<>();
	private String metodoPagamento = "emMaos";
	private String banco;
	private String agencia;
	private String contaCorrente;
	private Double debitoSindical = 0.0;

	// usado pelo XMLDecoder
	protected Empregado() {}

	protected Empregado(String nome, String endereco, double salario) throws SalarioNegativoException {
		this.nome = nome;
		this.endereco = endereco;
		setSalario(salario);
	}

	/* ---------- comportamento especifico de cada tipo ---------- */

	public abstract String getTipo();

	public abstract boolean recebeEm(LocalDate data);

	public abstract int diasDoPeriodo(LocalDate data);

	public abstract Pagamento calcularBruto(LocalDate dataPagamento);

	public boolean contratadoEm(LocalDate data) {
		return true;
	}

	/* ---------- taxas de servico ---------- */

	public void lancarTaxaServico(String data, double valor) {
		taxasServico.put(data, valor);
	}

	public double somarTaxasServico(LocalDate inicio, LocalDate fim) {
		return Datas.somarNoIntervalo(taxasServico, inicio, fim);
	}

	/* ---------- troca de tipo ---------- */

	// Copia sindicato, metodo de pagamento, debito e taxas de servico (usado quando o tipo muda)
	public void copiarDadosComuns(Empregado de) {
		this.sindicalizado = de.sindicalizado;
		this.idSindicato = de.idSindicato;
		this.taxaSindical = de.taxaSindical;
		this.metodoPagamento = de.metodoPagamento;
		this.banco = de.banco;
		this.agencia = de.agencia;
		this.contaCorrente = de.contaCorrente;
		this.debitoSindical = de.debitoSindical;
		this.taxasServico = new LinkedHashMap<>(de.taxasServico);
	}

	/* ---------- getters e setters ---------- */

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public double getSalario() {
		return salario;
	}

	public final void setSalario(double salario) throws SalarioNegativoException {
		if (salario < 0)
			throw new SalarioNegativoException();
		this.salario = salario;
	}

	public Boolean getSindicalizado() {
		return sindicalizado;
	}

	public void setSindicalizado(Boolean sindicalizado) {
		this.sindicalizado = sindicalizado != null && sindicalizado;
	}

	public String getIdSindicato() {
		return idSindicato;
	}

	public void setIdSindicato(String idSindicato) {
		this.idSindicato = idSindicato;
	}

	public Double getTaxaSindical() {
		return taxaSindical;
	}

	public void setTaxaSindical(Double taxaSindical) throws TaxaSindicalNegativaException {
		if (taxaSindical != null && taxaSindical < 0)
			throw new TaxaSindicalNegativaException();
		this.taxaSindical = taxaSindical;
	}

	public Map<String, Double> getTaxasServico() {
		return new LinkedHashMap<>(taxasServico);
	}

	public void setTaxasServico(Map<String, Double> taxasServico) {
		this.taxasServico = taxasServico == null ? new LinkedHashMap<>() : new LinkedHashMap<>(taxasServico);
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public Double getDebitoSindical() {
		return debitoSindical;
	}

	public void setDebitoSindical(Double debitoSindical) {
		this.debitoSindical = debitoSindical == null ? 0.0 : debitoSindical;
	}
}