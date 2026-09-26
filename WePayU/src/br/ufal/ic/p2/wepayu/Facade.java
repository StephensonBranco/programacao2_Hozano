package br.ufal.ic.p2.wepayu;

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
	/////////////////////////////////
	
	/////////////////////////////////
	public String getAtributoEmpregado(String emp, String atributo) throws Exception{
		return sistema.getAtributoEmpregado(emp, atributo);
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
	/////////////////////////////////
}

