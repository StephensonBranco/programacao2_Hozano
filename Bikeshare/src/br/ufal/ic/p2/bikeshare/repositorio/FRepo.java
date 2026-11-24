package br.ufal.ic.p2.bikeshare.repositorio;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;

import br.ufal.ic.p2.bikeshare.exception.ErroCarregarException;
import br.ufal.ic.p2.bikeshare.exception.ErroSalvarException;

public class FRepo {
	public FRepo() {
		
	}
	
	//Adicionar um elemento à lista enviada
	public static <T> String adicionar(Map<String, T> lista, String prefixo, T elemento, int id) {
		String id_s = prefixo + id;
		lista.put(id_s, elemento);
		return id_s;
	}
	
	//Adquirir um elemento da lista a partir do seu ID
	public static <T> T get(Map<String, T> lista, String key, Supplier<? extends Exception> e) throws Exception{
		T elemento = lista.get(key);
		if(elemento==null)
			throw e.get();
		
		return elemento;
	}
	
	
	//ARQUIVOS
	@SuppressWarnings("unchecked")
	public static <T> T carregar(File arquivo, T padrao) {		// T padrao: quando não existe um arquivo para carregar, então retorna um novo
		if (!arquivo.exists())
			return padrao;
		try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
			return (T) d.readObject();
		} catch (IOException e) {
			throw new ErroCarregarException(e);
		}
	}
	
	@SuppressWarnings("unused")
	public static void gravar(File arquivo, Object conteudo) {
		arquivo.getParentFile().mkdirs();
		try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)))) {
			e.setExceptionListener(ex -> System.err.println("Erro no XMLEncoder: " + ex));
			e.setPersistenceDelegate(LocalDateTime.class, new PersistenceDelegate() {
			    @Override
			    protected Expression instantiate(Object oldInstance, Encoder out) {
			        return new Expression(oldInstance, LocalDateTime.class, "parse",
			                new Object[]{ oldInstance.toString() });
			    }
			});
			e.writeObject(conteudo);
		} catch (IOException e) {
			throw new ErroSalvarException(e);
		}
	}
}
