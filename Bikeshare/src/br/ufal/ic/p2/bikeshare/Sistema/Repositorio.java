package br.ufal.ic.p2.bikeshare.Sistema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.beans.XMLDecoder;

import br.ufal.ic.p2.bikeshare.models.PessoaGen;

public class Repositorio {
	private static final File ARQUIVO_USUARIOS = new File("XML/usuarios.xml");
//	private static final File ARQUIVO_BICICLETAS = new File("/XML/bicicletas.xml");
	
	private Map<String, PessoaGen> Usuarios; 
	
	public Repositorio() {
		Usuarios = carregar(ARQUIVO_USUARIOS);
	}

	//Guardar os arquivos
	@SuppressWarnings("unchecked")
	private static <T> T carregar(File arquivo) {
		if (!arquivo.exists())
			return (T) new LinkedHashMap<>();
		try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
			return (T) d.readObject();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao carregar dados", e);
		}
	}
	
	@SuppressWarnings("unused")
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
