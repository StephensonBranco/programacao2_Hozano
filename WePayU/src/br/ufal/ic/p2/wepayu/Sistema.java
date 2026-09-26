package br.ufal.ic.p2.wepayu;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Locale;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.Comissionado;
import br.ufal.ic.p2.wepayu.models.Empregado;

public class Sistema {
	private static final File ARQUIVO = new File("XML/dados.xml");
	private Map<String, Empregado> empregados;
	
	public Sistema() { 
		empregados = decoder();
	}
	
	public void zerarSistema() {
		empregados.clear();
		ARQUIVO.delete();
	}

	public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException {

		Empregado empregado = empregados.get(emp);
		if(empregado==null) {
			throw new EmpregadoNaoExisteException();			
		}
		else if(atributo.equals("nome")) {
			return empregado.getNome();			
		}
		else if(atributo.equals("endereco")) {
			return empregado.getEndereco();
		}			
		else if(atributo.equals("tipo")) {
			return empregado.getTipo();			
		}
		else if(atributo.equals("salario")) {
			return String.format(Locale.of("pt", "BR"), "%.2f",empregado.getSalario());
		}
		else {
			String sindicalizado = String.valueOf(empregado.getSindicalizado());
			return sindicalizado;
		}
	}
	
	public String criarEmpregado(String nome, String endereco, String tipo, String salario) {
		double valor = Double.parseDouble(salario.replace(",", "."));
		String ID = UUID.randomUUID().toString();
		empregados.put(ID, new Empregado(nome, endereco, tipo, valor));
		return ID;
	}
	
	public String criarEmpregado(String nome, String endereco, String tipo, String salario, String taxaDeComissao) {
		double valor = Double.parseDouble(salario.replace(",", "."));
		double taxa = Double.parseDouble(taxaDeComissao.replace(",", "."));
		String ID = UUID.randomUUID().toString();
		empregados.put(ID, new Comissionado(nome, endereco, tipo, valor, taxa));
		return ID;
	}
	
	/*==============================================================*/
	/*==============================================================*/
	@SuppressWarnings("unchecked")
	public Map<String,Empregado> decoder() {
		if(!ARQUIVO.exists()) {
			return new HashMap<>();
		}
		
		try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(ARQUIVO)))) {
			return (Map<String,Empregado>)decoder.readObject();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao carregar dados", e);
		}
	}
	
	public void encoder(Map<String, Empregado> map){
		ARQUIVO.getParentFile().mkdir();
		try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(ARQUIVO)))) {
			encoder.writeObject(empregados);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar dados", e);
		}
	}
}
