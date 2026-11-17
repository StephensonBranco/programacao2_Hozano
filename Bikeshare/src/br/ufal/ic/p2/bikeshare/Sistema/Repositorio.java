package br.ufal.ic.p2.bikeshare.Sistema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.beans.XMLDecoder;

import br.ufal.ic.p2.bikeshare.exception.ErroCarregarException;
import br.ufal.ic.p2.bikeshare.exception.ErroSalvarException;
import br.ufal.ic.p2.bikeshare.exception.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Usuario;

public class Repositorio {
	private static final File ARQUIVO_USUARIOS = new File("XML/usuarios.xml");
//	private static final File ARQUIVO_BICICLETAS = new File("/XML/bicicletas.xml");
	private Map<String, Usuario> Usuarios; 
	
	public Repositorio() {
		Usuarios = carregar(ARQUIVO_USUARIOS);
	}
	
	public void salvar()  {
		gravar(ARQUIVO_USUARIOS, Usuarios);
	}

	//Guarda um usuario no repositorio em certo id
	public String adicionar(Usuario usuario) {
		String id = UUID.randomUUID().toString();
		Usuarios.put(id, usuario);
		return id;
	}
	
	public Usuario getUsuario(String key) throws Exception{
		Usuario usuario = Usuarios.get(key);
		if(usuario==null)
			throw new UsuarioNaoExisteException();
		
		return Usuarios.get(key);
	}
	
	public void zerarSistema() {
		Usuarios = new LinkedHashMap<>();
	}
	
	
	//Guardar os arquivos
	@SuppressWarnings("unchecked")
	private static <T> T carregar(File arquivo) {
		if (!arquivo.exists())
			return (T) new LinkedHashMap<>();
		try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
			return (T) d.readObject();
		} catch (IOException e) {
			throw new ErroCarregarException(e);
		}
	}
	
	@SuppressWarnings("unused")
	private static void gravar(File arquivo, Object conteudo) {
		arquivo.getParentFile().mkdirs();
		try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)))) {
			e.setExceptionListener(ex -> System.err.println("Erro no XMLEncoder: " + ex));
			e.writeObject(conteudo);
		} catch (IOException e) {
			throw new ErroSalvarException(e);
		}
	}
}
