package br.ufal.ic.p2.wepayu.sistema;

import java.io.File;
import java.time.LocalDate;

import br.ufal.ic.p2.wepayu.Exception.SistemaEncerradoException;

/*
 * Orquestrador: cada comando que altera estado passa pelo historico (undo/redo) e e delegado
 * para a classe responsavel. Consultas vao direto para ConsultaEmpregados.
 */
public class Sistema {
	private final RepositorioEmpregados repositorio = new RepositorioEmpregados();
	private final HistoricoUndoRedo historico = new HistoricoUndoRedo(repositorio);
	private final CalculadoraFolha calculadora = new CalculadoraFolha(repositorio);
	private final RelatorioFolha relatorio = new RelatorioFolha();
	private final ConsultaEmpregados consultas = new ConsultaEmpregados(repositorio);
	private final CadastroEmpregados cadastro = new CadastroEmpregados(repositorio);
	private boolean encerrado = false;
	
	/* ---------- consultas ---------- */

	public String getAtributoEmpregado(String emp, String atributo) throws Exception {
		return consultas.atributo(emp, atributo);
	}

	public String getEmpregadoPorNome(String nome, int indice) throws Exception {
		return consultas.porNome(nome, indice);
	}

	public String getHorasTrabalhadas(String emp, String dataInicial, String dataFinal, boolean extras) throws Exception {
		return consultas.horasTrabalhadas(emp, dataInicial, dataFinal, extras);
	}

	public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws Exception {
		return consultas.vendasRealizadas(emp, dataInicial, dataFinal);
	}

	public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws Exception {
		return consultas.taxasServico(emp, dataInicial, dataFinal);
	}

	public String totalFolha(String data) throws Exception {
		return Formatador.valor(calculadora.totalFolha(Validador.data(data, "")));
	}

	public int getNumeroDeEmpregados() {
		return repositorio.quantidade();
	}

	/* ---------- comandos (registram undo) ---------- */

	public void zerarSistema() {
		historico.executarSemExcecao(repositorio::zerar);
	}

	public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
		String[] id = new String[1];
		historico.executar(() -> id[0] = cadastro.criar(nome, endereco, tipo, salario, comissao));
		return id[0];
	}

	public void removerEmpregado(String emp) throws Exception {
		historico.executar(() -> repositorio.remover(emp));
	}

	public void alteraEmpregado(String emp, String atributo, String s1, String s2, String s3, String s4) throws Exception {
		historico.executar(() -> cadastro.alterar(emp, atributo, s1, s2, s3, s4));
	}

	public void lancaCartao(String emp, String data, String horas) throws Exception {
		historico.executar(() -> cadastro.lancarCartao(emp, data, horas));
	}

	public void lancaVenda(String emp, String data, String valor) throws Exception {
		historico.executar(() -> cadastro.lancarVenda(emp, data, valor));
	}

	public void lancaTaxaServico(String membro, String data, String valor) throws Exception {
		historico.executar(() -> cadastro.lancarTaxaServico(membro, data, valor));
	}

	public void rodaFolha(String data, String saida) throws Exception {
		historico.executar(() -> {
			LocalDate dataR = Validador.data(data, "");
			relatorio.escrever(new File(saida), dataR, calculadora.calcularFolha(dataR));
		});
	}

	/* ---------- undo / redo / encerramento ---------- */

	private void verificarEncerrado() throws Exception {
		if (encerrado)
			throw new SistemaEncerradoException();
	}

	public void undo() throws Exception {
		verificarEncerrado();
		historico.desfazer();
	}

	public void redo() throws Exception {
		verificarEncerrado();
		historico.refazer();
	}

	public void encerrarSistema() {
		encerrado = true;
		repositorio.salvar();
	}
	
	/* set e get para conseguir salvar o listaAgendaPagamento*/
}