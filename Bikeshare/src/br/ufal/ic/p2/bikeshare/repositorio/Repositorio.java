package br.ufal.ic.p2.bikeshare.repositorio;

import java.util.Map;

public interface Repositorio<T>{
	public void zerar();
	public void salvar();
	public void remover(String elemento) throws Exception;
	public String adicionar(T elemento) throws Exception;
	public T get(String elemento) throws Exception;
	public Map<String, T> getLista();
}
