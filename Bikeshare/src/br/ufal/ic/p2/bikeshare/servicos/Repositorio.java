package br.ufal.ic.p2.bikeshare.servicos;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.beans.XMLDecoder;

import br.ufal.ic.p2.bikeshare.exception.BicicleNaoEstaNaEstacaoException;
import br.ufal.ic.p2.bikeshare.exception.BicicletaJaExisteException;
import br.ufal.ic.p2.bikeshare.exception.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.ErroCarregarException;
import br.ufal.ic.p2.bikeshare.exception.ErroSalvarException;
import br.ufal.ic.p2.bikeshare.exception.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class Repositorio {
	private static final File ARQUIVO_USUARIOS = new File("XML/usuarios.xml");
	private static final File ARQUIVO_ESTACOES = new File("/XML/estacoes.xml");
	private static final File ARQUIVO_BICICLETAS = new File("/XML/bicicletas.xml");
	private static final File ARQUIVO_ID_USUARIOS = new File("XML/id_usuarios.xml");
	private static final File ARQUIVO_ID_ESTACOES = new File("XML/id_estacoes.xml");
	
	private Map<String, Usuario> usuarios;
	private Map<String, Estacao> estacoes;
	private Map<String, Bicicleta> bicicletas;
	private int id_atual_usuarios;	//para retornar o id de um novo usuário a partir do mais novo
	private int id_atual_estacoes;
	
	public Repositorio() {
		usuarios = carregar(ARQUIVO_USUARIOS, new LinkedHashMap<>());
		estacoes = carregar(ARQUIVO_ESTACOES, new LinkedHashMap<>());
		bicicletas = carregar(ARQUIVO_BICICLETAS, new LinkedHashMap<>());
		id_atual_usuarios = carregar(ARQUIVO_ID_USUARIOS, 1);
		id_atual_estacoes = carregar(ARQUIVO_ID_ESTACOES, 1);
	}
	
	public void salvar()  {
		gravar(ARQUIVO_USUARIOS, usuarios);
		gravar(ARQUIVO_ESTACOES, estacoes);
		gravar(ARQUIVO_BICICLETAS, bicicletas);
		gravar(ARQUIVO_ID_USUARIOS, id_atual_usuarios);
		gravar(ARQUIVO_ID_ESTACOES, id_atual_estacoes);
	}

	public void removerBicicleta(String estacao, String bicicleta) throws Exception {
		Validador.idEstacao(estacao);
		Validador.idBicicleta(bicicleta);
		
		if(!estacoes.containsKey(estacao))
			throw new EstacaoNaoExisteException();
		if(!bicicletas.containsKey(bicicleta))
			throw new BicicletaNaoExisteException();
		if(!bicicletas.get(bicicleta).getEstacao().equals(estacao))
			throw new BicicleNaoEstaNaEstacaoException();
		
		estacoes.get(estacao).removerBicicleta(bicicleta);
		bicicletas.remove(bicicleta);
	}
	
	public void removerUsuario(String usuario) throws Exception{
		Validador.idUsuario(usuario);
		if(!usuarios.containsKey(usuario)) {
			throw new UsuarioNaoExisteException();
		}
		usuarios.remove(usuario);
	}
	
	//Guarda um elemento no repositorio para o novo maior ID
	private <T> String adicionar(Map<String, T> lista, String prefixo, T elemento, int id) {
		String id_s = prefixo + id;
		lista.put(id_s, elemento);
		return id_s;
	}
	
	
	public String adicionarUsuario(Usuario usuario) {
		return adicionar(usuarios, "U", usuario, id_atual_usuarios++);
	}
	
	
	public String adicionarEstacao(Estacao estacao) {
		return adicionar(estacoes, "E", estacao, id_atual_estacoes++);
	}
	
	//id é o nome da bicicleta "bc1, be1..."
	public void adicionarBicicleta(String estacao, String id, Bicicleta bicicleta) throws Exception {
		Estacao station = getEstacao(estacao);
		Validador.idBicicleta(id);
		if(bicicletas.containsKey(id))
			throw new BicicletaJaExisteException();
		station.adicionarBicicleta(id);
		bicicletas.put(id, bicicleta);
	}
	
	//Pega um usuário do banco a partir do id dele
	private <T> T getElemento(Map<String, T> lista, String key, Supplier<? extends Exception> e) throws Exception{
		T elemento = lista.get(key);
		if(elemento==null)
			throw e.get();
		
		return elemento;
	}
	
	public Usuario getUsuario(String key) throws Exception {
		Validador.idUsuario(key);
		return getElemento(usuarios, key, UsuarioNaoExisteException::new);
	}
	
	public Estacao getEstacao(String key) throws Exception {
		Validador.idEstacao(key);
		return getElemento(estacoes, key, EstacaoNaoExisteException::new);
	}
	
	public Bicicleta getBicicleta(String key) throws Exception {
		Validador.idBicicleta(key);
		return getElemento(bicicletas, key, BicicletaNaoExisteException::new);
	}
	
	//Dá uma cópia dos usuários
	public Map<String, Usuario> getListaUsuarios() {
		return new LinkedHashMap<String, Usuario>(usuarios);
	}
	
	public Map<String, Estacao> getListaEstacoes() {
		return new LinkedHashMap<String, Estacao>(estacoes);
	}
	
	public void zerarSistema() {
		usuarios.clear();
		estacoes.clear();
		bicicletas.clear();
		id_atual_usuarios = 1;
		id_atual_estacoes = 1;
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
