package br.ufal.ic.p2.bikeshare.servicos;

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
import br.ufal.ic.p2.bikeshare.exception.IdentificacaoNulaException;
import br.ufal.ic.p2.bikeshare.exception.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Usuario;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class Repositorio {
	private static final File ARQUIVO_USUARIOS = new File("XML/usuarios.xml");
	private static final File ARQUIVO_ID = new File("XML/id.xml");
//	private static final File ARQUIVO_BICICLETAS = new File("/XML/bicicletas.xml");
	
	private Map<String, Usuario> usuarios;
	private int id_atual;	//para retornar o id de um novo usuário a partir do mais novo
	
	public Repositorio() {
		usuarios = carregar(ARQUIVO_USUARIOS, new LinkedHashMap<>());
		id_atual = carregar(ARQUIVO_ID, 1);
	}
	
	public void salvar()  {
		gravar(ARQUIVO_USUARIOS, usuarios);
		gravar(ARQUIVO_ID, id_atual);
	}

	//Guarda um usuario no repositorio em certo id
	public String adicionar(Usuario usuario) {
		String id = "U" + (id_atual++);
		usuarios.put(id, usuario);
		return id;
	}
	
	//Pega um usuário do banco a partir do id dele
	public Usuario getUsuario(String key) throws Exception{
		Validador.id(key);
		
		Usuario usuario = usuarios.get(key);
		if(usuario==null)
			throw new UsuarioNaoExisteException();
		
		return usuarios.get(key);
	}
	
	//Dá uma cópia dos usuários
	public Map<String, Usuario> getListaUsuarios() {
		return new LinkedHashMap<String, Usuario>(usuarios);
	}
	
	public void zerarSistema() {
		usuarios.clear();
		id_atual = 1;
	}
	
	
	////////////ARQUIVOS////////////
	@SuppressWarnings("unchecked")
	private static <T> T carregar(File arquivo, T padrao) {		// T padrao: quando não existe um arquivo para carregar, então retorna um novo
		if (!arquivo.exists())
			return padrao;
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
