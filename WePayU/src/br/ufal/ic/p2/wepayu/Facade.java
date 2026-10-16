package br.ufal.ic.p2.wepayu;
import br.ufal.ic.p2.wepayu.sistema.*;

//UTILIDADE DOS METODOS NO SISTEMA
//MÉTODOS COM FUNCOES PRÓXIMAS AGRUPADOS
public class Facade {
	private Sistema sistema = new Sistema();
	
	/////////////////////////////////
	public void zerarSistema() {
		sistema.zerarSistema();
	}
	
	public void encerrarSistema() {
		sistema.encerrarSistema();
	}
	/////////////////////////////////
	
	/////////////////////////////////
	public void lancaCartao(String emp, String data, String horas) throws Exception{
		sistema.lancaCartao(emp, data, horas);
	}
	
	public void lancaVenda(String emp, String data, String valor) throws Exception {
		sistema.lancaVenda(emp, data, valor);
	}
	
	public void lancaTaxaServico(String membro, String data, String valor) throws Exception{
		sistema.lancaTaxaServico(membro, data, valor);
	}
	/////////////////////////////////
	
	/////////////////////////////////
	public String getAtributoEmpregado(String emp, String atributo) throws Exception{
		return sistema.getAtributoEmpregado(emp, atributo);
	}
	
	public int getNumeroDeEmpregados() {
		return sistema.getNumeroDeEmpregados();
	}
	
	public String getEmpregadoPorNome(String nome, int indice) throws Exception{
		return sistema.getEmpregadoPorNome(nome, indice);
	}
	
	public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception{
		return sistema.getHorasTrabalhadas(emp, dataInicial, dataFinal, false);
	}
	
	public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception{
		return sistema.getHorasTrabalhadas(emp, dataInicial, dataFinal, true);
	}
	
	public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws Exception{
		return sistema.getVendasRealizadas(emp, dataInicial, dataFinal);
	}
	
	public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws Exception{
		return sistema.getTaxasServico(emp, dataInicial, dataFinal);
	}
	/////////////////////////////////
	
	/////////////////////////////////
	public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception{
		return sistema.criarEmpregado(nome, endereco, tipo, salario, null);
	}
	
	public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception{
		return sistema.criarEmpregado(nome, endereco, tipo, salario, comissao);
	}
	
	public void removerEmpregado(String emp) throws Exception{
		sistema.removerEmpregado(emp);
	}
	
	public void alteraEmpregado(String emp, String atributo, String valor1) throws Exception{
		sistema.alteraEmpregado(emp, atributo, valor1, null, null, null);
	}
	
	public void alteraEmpregado(String emp, String atributo, String valor, String comissao) throws Exception{
		sistema.alteraEmpregado(emp, atributo, valor, comissao, null, null);
	}
	
	public void alteraEmpregado(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws Exception{
		sistema.alteraEmpregado(emp, atributo, valor, idSindicato, taxaSindical, null);
	}
	
	public void alteraEmpregado(String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws Exception{
		sistema.alteraEmpregado(emp, atributo, valor1, banco, agencia, contaCorrente);
	}
	/////////////////////////////////

	/////////////////////////////////
	public String totalFolha(String data) throws Exception{
		return sistema.totalFolha(data);
	}
	
	public void rodaFolha(String data, String saida) throws Exception{
		sistema.rodaFolha(data, saida);
	}
	/////////////////////////////////

	/////////////////////////////////
	public void undo() throws Exception{
		sistema.undo();
	}
	
	public void redo() throws Exception{
		sistema.redo();
	}
	/////////////////////////////////
}