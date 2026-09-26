package br.ufal.ic.p2.wepayu;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Locale;

import br.ufal.ic.p2.wepayu.Exception.AtributoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.IdentificacaoNulaException;
import br.ufal.ic.p2.wepayu.Exception.NuloException;
import br.ufal.ic.p2.wepayu.Exception.ValorNegativoException;
import br.ufal.ic.p2.wepayu.Exception.ValorNaoNumericoException;
import br.ufal.ic.p2.wepayu.Exception.TipoInvalidoException;
import br.ufal.ic.p2.wepayu.Exception.TipoNaoAplicavelException;
import br.ufal.ic.p2.wepayu.models.Comissionado;
import br.ufal.ic.p2.wepayu.models.Empregado;

public class Sistema {
	private static final File ARQUIVO = new File("XML/dados.xml");
	private Map<String, Empregado> empregados;
	
	//Inicia o sistema pegando os dados salvos no XML
	public Sistema() { 
		empregados = decoder();
	}
	
	//Apaga o que tiver no Map empregados e apaga o XML
	public void zerarSistema() {
		empregados.clear();
		ARQUIVO.delete();
	}
	
	//Salva o que tem em empregados no dados.XML
	public void encerrarSistema() {
		encoder(empregados);
	}
	

	//Pega algum atributo de algum empregado
	public String getAtributoEmpregado(String emp, String atributo) throws Exception {
		String[] nomes = {"nome", "endereco", "tipo", "salario", "comissao", "sindicalizado"};
		if(emp.isEmpty()) {
			throw new IdentificacaoNulaException();
		}
		else if(Arrays.stream(nomes).noneMatch(nome -> nome.equals(atributo))) {
			throw new AtributoNaoExisteException();
		}
		
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
		else if(atributo.equals("comissao")) {
			Comissionado com = (Comissionado)empregado;
			return String.format(Locale.of("pt", "BR"), "%.2f", com.getComissao());
		}
		else {
			String sindicalizado = String.valueOf(empregado.getSindicalizado());
			return sindicalizado;
		}
	}
	
	//Lista gigante de exceções do criarEmpregados
	private void criarEmpregadoExceptions(String nome, String endereco, String tipo, String salario, String comissao) throws Exception{
		
		//Erros de atributos vazios
		String[] atributos = {nome, endereco, tipo, salario};
		String[] nomes = {"Nome", "Endereco", "Tipo", "Salario"};
		int i = 0;
		for (String atributo : atributos){
			if(atributo.equals(""))
				throw new NuloException(nomes[i]);
			i++;
		}
		
		if(!tipo.equals("horista") && !tipo.equals("comissionado") && !tipo.equals("assalariado")) {
			throw new TipoInvalidoException();
		}
		else if(salario.toCharArray()[0] == '-') {
			throw new ValorNegativoException("Salario");
		}
		else if(!salario.matches("[0-9]+(,[0-9]+)?")) {
			throw new ValorNaoNumericoException("Salario");
		}
		else if(tipo.equals("comissionado") && comissao == null) {
			throw new TipoNaoAplicavelException();
		}
		
		//Erros em geral de quando tem comissao
		else if(comissao != null) {
			if(comissao.equals("")) {
				throw new NuloException("Comissao");				
			}
			else if(comissao.toCharArray()[0] == '-') {
				throw new ValorNegativoException("Comissao");
			}
			else if(!comissao.matches("[0-9]+(,[0-9]+)?")) {
				throw new ValorNaoNumericoException("Comissao");
			}
			else if(!tipo.equals("comissionado")) {
				throw new TipoNaoAplicavelException();
			}
		}
	}
	
	//Criar empregado, podendo ou não receber o atributo comissao
	public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception{
		criarEmpregadoExceptions(nome, endereco, tipo, salario, comissao);
		String ID = UUID.randomUUID().toString();
		double valor = Double.parseDouble(salario.replace(",", "."));
		
		if(comissao != null) {
			double taxa = Double.parseDouble(comissao.replace(",","."));
			empregados.put(ID, new Comissionado(nome, endereco, tipo, valor, taxa));
		}
		else {
			empregados.put(ID, new Empregado(nome, endereco, tipo, valor));			
		}
		
		return ID;
	}
	
	/*==============================================================*/
	/*==============================================================*/
	
	@SuppressWarnings("unchecked")
	//Abrir o arquivo para salver no empregados
	private Map<String,Empregado> decoder() {
		if(!ARQUIVO.exists()) {
			return new HashMap<>();
		}
		
		try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(ARQUIVO)))) {
			return (Map<String,Empregado>)decoder.readObject();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao carregar dados", e);
		}
	}
	
	//Salvar as informações de empregados no dados.xml
	private void encoder(Map<String, Empregado> map){
		ARQUIVO.getParentFile().mkdir();
		try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(ARQUIVO)))) {
			encoder.writeObject(map);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar dados", e);
		}
	}
}
