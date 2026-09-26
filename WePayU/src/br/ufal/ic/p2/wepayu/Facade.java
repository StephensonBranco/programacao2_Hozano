package br.ufal.ic.p2.wepayu;


//UTILIDADE DAS FUNÇÕES NO SISTEMA
public class Facade {
	private Sistema sistema = new Sistema();
	
	public void zerarSistema() {
		sistema.zerarSistema();
	}
	
	public void encerrarSistema() {
		sistema.encerrarSistema();
	}
	
	public String getAtributoEmpregado(String emp, String atributo) throws Exception{
		return sistema.getAtributoEmpregado(emp, atributo);
	}
	
	public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception{
		return sistema.criarEmpregado(nome, endereco, tipo, salario, null);
	}
	
	public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception{
		return sistema.criarEmpregado(nome, endereco, tipo, salario, comissao);
	}
}

