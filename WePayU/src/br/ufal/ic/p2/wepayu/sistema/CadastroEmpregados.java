package br.ufal.ic.p2.wepayu.sistema;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Assalariado;
import br.ufal.ic.p2.wepayu.models.Comissionado;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Horista;

/*
 * Tudo o que altera dados de empregados: criacao, alteracao de atributos (inclusive troca de tipo)
 * e lancamentos (cartao de ponto, vendas e taxas de servico). As consultas ficam em ConsultaEmpregados.
 */
public class CadastroEmpregados {
	private final RepositorioEmpregados repo;

	public CadastroEmpregados(RepositorioEmpregados repo) {
		this.repo = repo;
	}

	/*==============================================================*/
	/* CRIACAO */
	/*==============================================================*/

	public String criar(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
		Validador.criacao(nome, endereco, tipo, salario, comissao);
		double valor = Validador.salario(salario);

		Empregado novo;
		switch (tipo) {
			case "comissionado":
				novo = new Comissionado(nome, endereco, valor, Validador.comissao(comissao));
				break;
			case "horista":
				novo = new Horista(nome, endereco, valor);
				break;
			default:
				novo = new Assalariado(nome, endereco, valor);
				break;
		}
		return repo.adicionar(novo);
	}

	/*==============================================================*/
	/* ALTERACAO */
	/*==============================================================*/

	/*
	 * Os valores das String podem ser:
	 * atributo = atributo, s1 = valor
	 * atributo = tipo, s1 = comissionado, s2 = comissao | s1 = horista, s2 = salario | s1 = assalariado, s2 = salario (opcional)
	 * atributo = sindicalizado, s1 = false | s1 = true, s2 = idSindicato, s3 = taxaSindical
	 * atributo = metodoPagamento, s1 = banco, s2 = qualBanco, s3 = agencia, s4 = contaCorrente
	 */
	public void alterar(String emp, String atributo, String s1, String s2, String s3, String s4) throws Exception {
		Empregado empregado = repo.buscar(emp);
		Validador.atributo(atributo);

		switch (atributo) {
			case "nome":
				if (s1.isEmpty())
					throw new NomeNuloException();
				empregado.setNome(s1);
				break;
			case "endereco":
				if (s1.isEmpty())
					throw new EnderecoNuloException();
				empregado.setEndereco(s1);
				break;
			case "tipo":
				alterarTipo(emp, empregado, s1, s2);
				break;
			case "metodoPagamento":
				alterarMetodoPagamento(empregado, s1, s2, s3, s4);
				break;
			case "sindicalizado":
				alterarSindicalizado(empregado, s1, s2, s3);
				break;
			case "comissao":
				double comissao = Validador.comissao(s1);
				Validador.como(empregado, Comissionado.class, EmpregadoNaoEhComissionadoException::new).setComissao(comissao);
				break;
			default: // salario
				empregado.setSalario(Validador.salario(s1));
				break;
		}
	}

	// Alterar o tipo exige um novo objeto (a classe muda)
	private void alterarTipo(String id, Empregado antigo, String tipo, String s2) throws Exception {
		Validador.tipo(tipo);

		String nome = antigo.getNome();
		String endereco = antigo.getEndereco();
		Empregado novo;
		switch (tipo) {
			case "comissionado":
				novo = new Comissionado(nome, endereco, antigo.getSalario(), Validador.comissao(s2));
				break;
			case "horista":
				novo = new Horista(nome, endereco, Validador.salario(s2));
				break;
			default:
				novo = new Assalariado(nome, endereco, s2 == null ? antigo.getSalario() : Validador.salario(s2));
				break;
		}
		novo.copiarDadosComuns(antigo);
		repo.substituir(id, novo);
	}

	private void alterarMetodoPagamento(Empregado empregado, String metodo, String banco, String agencia, String conta) throws Exception {
		Validador.metodoPagamento(metodo, banco, agencia, conta);
		empregado.setMetodoPagamento(metodo);
		empregado.setBanco(banco);
		empregado.setAgencia(agencia);
		empregado.setContaCorrente(conta);
	}

	private void alterarSindicalizado(Empregado empregado, String valor, String idSindicato, String taxa) throws Exception {
		boolean sindicalizado = Validador.booleano(valor);
		if (!sindicalizado) {
			empregado.setSindicalizado(false);
			return;
		}

		double taxaSindical = Validador.sindicalizacao(idSindicato, taxa);
		boolean idEmUso = repo.todos().values().stream()
				.anyMatch(e -> idSindicato.equals(e.getIdSindicato()));
		if (idEmUso)
			throw new IdSindicatoJaExisteException();

		empregado.setSindicalizado(true);
		empregado.setIdSindicato(idSindicato);
		empregado.setTaxaSindical(taxaSindical);
	}

	/*==============================================================*/
	/* LANCAMENTOS */
	/*==============================================================*/

	public void lancarCartao(String emp, String data, String horas) throws Exception {
		Horista horista = repo.buscar(emp, Horista.class, EmpregadoNaoEhHoristaException::new);
		Validador.data(data, "");
		horista.lancarHoras(data, Validador.positivo(horas, HorasNaoPositivaException::new));
	}

	public void lancarVenda(String emp, String data, String valor) throws Exception {
		Comissionado comissionado = repo.buscar(emp, Comissionado.class, EmpregadoNaoEhComissionadoException::new);
		Validador.data(data, "");
		comissionado.lancarVenda(data, Validador.positivo(valor, ValorNaoPositivoException::new));
	}

	public void lancarTaxaServico(String membro, String data, String valor) throws Exception {
		if (membro.isEmpty())
			throw new IdentificacaoDoMembroNulaException();

		Empregado empregado = repo.todos().values().stream()
				.filter(e -> e.getSindicalizado() && e.getIdSindicato().equals(membro))
				.findFirst()
				.orElseThrow(MembroNaoExisteException::new);

		double taxa = Validador.positivo(valor, ValorPositivoException::new);
		Validador.data(data, "");
		empregado.lancarTaxaServico(data, taxa);
	}
}