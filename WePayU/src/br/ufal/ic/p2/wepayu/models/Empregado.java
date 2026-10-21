package br.ufal.ic.p2.wepayu.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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
	private static final LocalDate PRIMEIRA_SEMANA = LocalDate.of(2005, 1, 1);
	
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
	protected String agendaPagamento;
	
	// usado pelo XMLDecoder
	protected Empregado() {}

	protected Empregado(String nome, String endereco, double salario) throws SalarioNegativoException {
		this.nome = nome;
		this.endereco = endereco;
		setSalario(salario);
	}

	/* ---------- comportamento especifico de cada tipo ---------- */

	public abstract String getTipo();

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
	
	/* --- pegar dados da agendaPagemento --- */
	
	private String[] agenda() {
	    return agendaPagamento.trim().split("\\s+");
	}

	private int intervaloSemanas() {
	    String[] p = agenda();
	    return p.length == 3 ? Integer.parseInt(p[1]) : 1;
	}

	private boolean ehMensal() {
	    return agenda()[0].equals("mensal");
	}

	public boolean recebeEm(LocalDate data) {
	    String[] p = agenda();

	    if (ehMensal()) {
	        if (p[1].equals("$")) {
	            return ultimoDiaUtil(data).equals(data);
	        }
	        int dia = Math.min(Integer.parseInt(p[1]), data.lengthOfMonth());
	        return dia == data.getDayOfMonth();
	    }

	    int intervalo = intervaloSemanas();
	    int diaDaSemana = Integer.parseInt(p[p.length - 1]);

	    if (data.getDayOfWeek().getValue() != diaDaSemana) {
	        return false;
	    }
	    if (intervalo == 1) {
	        return true;
	    }
	    // blocos de 7 dias a partir de 01/01/2005; paga ao FIM de cada intervalo completo
	    long bloco = ChronoUnit.WEEKS.between(PRIMEIRA_SEMANA, data);
	    return (bloco + 1) % intervalo == 0;
	}

	// Primeiro dia (inclusive) do período que termina em dataPagamento
	public LocalDate inicioPeriodo(LocalDate dataPagamento) {
	    if (ehMensal()) {
	        return agenda()[1].equals("$")
	                ? dataPagamento.withDayOfMonth(1)
	                : dataPagamento.minusMonths(1).plusDays(1);
	    }
	    return dataPagamento.minusDays(7L * intervaloSemanas() - 1);
	}

	public int diasDoPeriodo(LocalDate dataPagamento) {
	    return (int) ChronoUnit.DAYS.between(inicioPeriodo(dataPagamento), dataPagamento) + 1;
	}

	// Salário fixo do período (assalariado e parte fixa do comissionado)
	protected double fixoDoPeriodo() {
	    return ehMensal()
	            ? getSalario()
	            : getSalario() * 12.0 / 52.0 * intervaloSemanas();
	}
	
	private static LocalDate ultimoDiaUtil(LocalDate data) {
		LocalDate dia = data.with(TemporalAdjusters.lastDayOfMonth());
		while (dia.getDayOfWeek() == DayOfWeek.SATURDAY || dia.getDayOfWeek() == DayOfWeek.SUNDAY)
			dia = dia.minusDays(1);
		return dia;
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

	public String getAgendaPagamento() {
		return agendaPagamento;
	}

	public void setAgendaPagamento(String agendaDePagamento) {
		this.agendaPagamento = agendaDePagamento;
	}
}