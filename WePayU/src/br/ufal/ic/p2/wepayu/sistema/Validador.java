package br.ufal.ic.p2.wepayu.sistema;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Supplier;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.util.Datas;

public final class Validador {
	private static final String NUMERO = "[0-9]+(,[0-9]+)?";

	public record Periodo(LocalDate inicio, LocalDate fim) {}

	private Validador() {}

	/* ---------- datas ---------- */

	public static LocalDate data(String data, String erro) throws Exception {
		try {
			return Datas.parse(data);
		} catch (DateTimeException e) {
			switch (erro) {
				case "inicial":
					throw new DataInicialInvalidaException();
				case "final":
					throw new DataFinalInvalidaException();
				default:
					throw new DataInvalidaException();
			}
		}
	}

	// valida as duas datas e a ordem entre elas
	public static Periodo periodo(String inicial, String fim) throws Exception {
		LocalDate dataI = data(inicial, "inicial");
		LocalDate dataF = data(fim, "final");
		if (dataI.isAfter(dataF))
			throw new DataInicialAposFinalException();
		return new Periodo(dataI, dataF);
	}

	/* ---------- numeros: unico lugar para vazio / negativo / nao numerico ---------- */

	private static double numero(String v, Supplier<? extends Exception> nulo,
			Supplier<? extends Exception> negativo, Supplier<? extends Exception> naoNumerico) throws Exception {
		if (v == null || v.isEmpty())
			throw nulo.get();
		if (v.charAt(0) == '-')
			throw negativo.get();
		if (!v.matches(NUMERO))
			throw naoNumerico.get();
		return Double.parseDouble(v.replace(',', '.'));
	}

	public static double salario(String v) throws Exception {
		return numero(v, SalarioNuloException::new, SalarioNegativoException::new, SalarioNaoNumericoException::new);
	}

	public static double comissao(String v) throws Exception {
		return numero(v, ComissaoNulaException::new, ComissaoNegativaException::new, ComissaoNaoNumericaException::new);
	}

	public static double taxaSindical(String v) throws Exception {
		return numero(v, TaxaSindicalNulaException::new, TaxaSindicalNegativaException::new, TaxaSindicalNaoNumericaException::new);
	}

	// horas, vendas e taxas de servico: numero que precisa ser > 0
	public static double positivo(String v, Supplier<? extends Exception> erro) throws Exception {
		double valor = numero(v, erro, erro, erro);
		if (valor <= 0)
			throw erro.get();
		return valor;
	}

	/* ---------- tipo e atributo ---------- */

	public static void tipo(String tipo) throws Exception {
		if (!tipo.equals("horista") && !tipo.equals("comissionado") && !tipo.equals("assalariado"))
			throw new TipoInvalidoException();
	}

	// Cast em um unico lugar: devolve o empregado como a subclasse esperada ou lanca o erro
	public static <T extends Empregado> T como(Empregado emp, Class<T> classe,
			Supplier<? extends Exception> erro) throws Exception {
		if (!classe.isInstance(emp))
			throw erro.get();
		return classe.cast(emp);
	}

	public static void atributo(String atributo) throws Exception {
		String[] nomes = {"nome", "endereco", "tipo", "salario", "comissao",
				"sindicalizado", "metodoPagamento", "banco", "agencia",
				"contaCorrente", "idSindicato", "taxaSindical", "agendaPagamento"};
		if (Arrays.stream(nomes).noneMatch(nome -> nome.equals(atributo)))
			throw new AtributoNaoExisteException();
	}

	/* ---------- regras de criacao e alteracao ---------- */

	public static void criacao(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
		if (nome.isEmpty())
			throw new NomeNuloException();
		if (endereco.isEmpty())
			throw new EnderecoNuloException();
		if (tipo.isEmpty())
			throw new TipoNuloException();
		if (salario.isEmpty())
			throw new SalarioNuloException();

		tipo(tipo);
		salario(salario);

		boolean comissionado = tipo.equals("comissionado");
		if (comissionado && comissao == null)
			throw new TipoNaoAplicavelException();
		if (comissao != null) {
			comissao(comissao);
			if (!comissionado)
				throw new TipoNaoAplicavelException();
		}
	}

	public static boolean booleano(String s) throws Exception {
		if (!s.equals("true") && !s.equals("false"))
			throw new BooleanoException();
		return Boolean.parseBoolean(s);
	}

	// retorna a taxa sindical ja convertida
	public static double sindicalizacao(String idSindicato, String taxa) throws Exception {
		if (idSindicato.isEmpty())
			throw new IdentificacaoDoSindicatoNulaException();
		return taxaSindical(taxa);
	}

	public static void metodoPagamento(String metodo, String banco, String agencia, String conta) throws Exception {
		if (!metodo.equals("emMaos") && !metodo.equals("banco") && !metodo.equals("correios"))
			throw new MetodoDePagamentoInvalidoException();
		if (metodo.equals("banco")) {
			if (banco.isEmpty())
				throw new BancoNuloException();
			if (agencia.isEmpty())
				throw new AgenciaNuloException();
			if (conta.isEmpty())
				throw new ContaCorrenteNuloException();
		}
	}
}