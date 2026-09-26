package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

public class Facade {
	private Sistema sistema = new Sistema();
	
	public void zerarSistema() {
		sistema.zerarSistema();
	}
	
	public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException{
		return sistema.getAtributoEmpregado(emp, atributo);
	}
	
	public String criarEmpregado(String nome, String endereco, String tipo, String salario) {
		return sistema.criarEmpregado(nome, endereco, tipo, salario);
	}
	
	public String criarEmpregado(String nome, String endereco, String tipo, String salario, String taxaDeComissao) {
		return sistema.criarEmpregado(nome, endereco, tipo, salario, taxaDeComissao);
	}
}

