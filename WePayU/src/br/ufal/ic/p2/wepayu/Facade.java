package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;  
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.Sistema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import  java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import  java.beans.XMLDecoder;

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
}

