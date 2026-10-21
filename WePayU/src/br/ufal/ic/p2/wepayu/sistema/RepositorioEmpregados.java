package br.ufal.ic.p2.wepayu.sistema;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Empregado;

public class RepositorioEmpregados {
	private static final File ARQUIVO = new File("XML/dados.xml");
	// Quanto de cada taxa de servico ja foi descontado em folhas (id -> data -> valor)
	private static final File ARQUIVO_COBRANCAS = new File("XML/taxas_cobradas.xml");
	private static final File ARQUIVO_AGENDA_PAGAMENTOS = new File("XML/agenda_pagamentos.xml");
	private Map<String, Empregado> empregados;
	private Map<String, Map<String, Double>> taxasCobradas;
	private List<String> listaAgendaPagamentos;
	private List<String> agendaDefault = List.of("semanal 5", "mensal $", "semanal 2 5");

	public RepositorioEmpregados() {
		empregados = carregar(ARQUIVO, new LinkedHashMap<>());
		taxasCobradas = carregar(ARQUIVO_COBRANCAS, new LinkedHashMap<>());
		listaAgendaPagamentos = carregar(ARQUIVO_AGENDA_PAGAMENTOS, new ArrayList<>());
		if(listaAgendaPagamentos.size() < agendaDefault.size()) {
			listaAgendaPagamentos.addAll(agendaDefault);
		}
	}

	/* ---------- empregados ---------- */

	// Visao somente leitura. Sempre peca ao repositorio: o undo/redo troca as instancias
	public Map<String, Empregado> todos() {
		return Collections.unmodifiableMap(empregados);
	}

	public int quantidade() {
		return empregados.size();
	}

	public Empregado buscar(String id) throws Exception {
		if (id.isEmpty())
			throw new IdentificacaoDoEmpregadoNulaException();
		Empregado empregado = empregados.get(id);
		if (empregado == null)
			throw new EmpregadoNaoExisteException();
		return empregado;
	}

	// Busca ja convertendo para a subclasse esperada (ou lanca o erro informado)
	public <T extends Empregado> T buscar(String id, Class<T> classe, Supplier<? extends Exception> erro) throws Exception {
		return Validador.como(buscar(id), classe, erro);
	}

	public String adicionar(Empregado empregado) {
		String id = UUID.randomUUID().toString();
		empregados.put(id, empregado);
		return id;
	}

	public void substituir(String id, Empregado novo) {
		empregados.put(id, novo);
	}

	public void remover(String id) throws Exception {
		buscar(id);
		empregados.remove(id);
	}

	// Pegar informações da listaAgendaPagamento
	
	public boolean existeAgenda(String agenda) throws Exception{
		return listaAgendaPagamentos.stream().anyMatch(a -> a.equals(agenda));			
	}
	
	
	/* ---------- taxas de servico ja cobradas ---------- */

	public double getCobrado(String idEmpregado, String data) {
		Map<String, Double> doEmpregado = taxasCobradas.get(idEmpregado);
		if (doEmpregado == null)
			return 0.0;
		return doEmpregado.getOrDefault(data, 0.0);
	}

	public void registrarCobranca(String idEmpregado, String data, double valor) {
		taxasCobradas
				.computeIfAbsent(idEmpregado, k -> new LinkedHashMap<>())
				.merge(data, valor, Double::sum);
	}

	public void zerar() {
		empregados.clear();
		taxasCobradas.clear();
		ARQUIVO.delete();
		ARQUIVO_COBRANCAS.delete();
	}

	public void salvar() {
		gravar(ARQUIVO, empregados);
		gravar(ARQUIVO_COBRANCAS, taxasCobradas);
		gravar(ARQUIVO_AGENDA_PAGAMENTOS, listaAgendaPagamentos);
	}

	/* ---------- fotos para undo/redo (copia profunda via XMLEncoder) ---------- */

	public byte[] tirarFoto() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		List<Object> estado = new ArrayList<>();
		estado.add(empregados);
		estado.add(taxasCobradas);
		try (XMLEncoder enc = new XMLEncoder(bos)) {
			enc.writeObject(estado);
		}
		return bos.toByteArray();
	}

	@SuppressWarnings("unchecked")
	public void restaurarFoto(byte[] foto) {
		try (XMLDecoder dec = new XMLDecoder(new ByteArrayInputStream(foto))) {
			List<Object> estado = (List<Object>) dec.readObject();
			empregados = (Map<String, Empregado>) estado.get(0);
			taxasCobradas = (Map<String, Map<String, Double>>) estado.get(1);
		}
	}

	/* ---------- arquivo ---------- */

	@SuppressWarnings("unchecked")
	private static <T> T carregar(File arquivo, T padrao) {
		if (!arquivo.exists()) {
			return padrao;			
		}
		try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
			return (T) d.readObject();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao carregar dados", e);
		}
	}

	private static void gravar(File arquivo, Object conteudo) {
		arquivo.getParentFile().mkdirs();
		try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)))) {
			e.setExceptionListener(ex -> System.err.println("Erro no XMLEncoder: " + ex));
			e.writeObject(conteudo);
		} catch (IOException ex) {
			throw new RuntimeException("Erro ao salvar dados", ex);
		}
	}
}