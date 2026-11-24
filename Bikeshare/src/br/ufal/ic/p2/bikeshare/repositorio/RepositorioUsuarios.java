package br.ufal.ic.p2.bikeshare.repositorio;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.bikeshare.exception.UsuarioComAluguelAtivoException;
import br.ufal.ic.p2.bikeshare.exception.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class RepositorioUsuarios implements Repositorio<Usuario>{
	private static final File ARQUIVO_USUARIOS = new File("XML/usuarios.xml");
	private static final File ARQUIVO_ID_USUARIOS = new File("XML/id_usuarios.xml");

	private Map<String, Usuario> usuarios;
	private int id_atual_usuarios;	//para retornar o id de um novo usuário a partir do mais novo
	
	
	public RepositorioUsuarios() {
		usuarios = FRepo.carregar(ARQUIVO_USUARIOS, new LinkedHashMap<>());
		id_atual_usuarios = FRepo.carregar(ARQUIVO_ID_USUARIOS, 1);
	}

	@Override
	public void zerar() {
		usuarios.clear();
		id_atual_usuarios = 1;
	}
	
	@Override
	public void salvar() {
		FRepo.gravar(ARQUIVO_USUARIOS, usuarios);
		FRepo.gravar(ARQUIVO_ID_USUARIOS, id_atual_usuarios);
	}


	@Override
	public void remover(String usuario) throws Exception {
		Validador.idUsuario(usuario);
		if(!usuarios.containsKey(usuario))
			throw new UsuarioNaoExisteException();
		else if(!"nenhum".equals(usuarios.get(usuario).getAluguelAtivo()))
			throw new UsuarioComAluguelAtivoException();
		usuarios.remove(usuario);
	}


	@Override
	public String adicionar(Usuario usuario) {
		return FRepo.adicionar(usuarios, "U", usuario, id_atual_usuarios++);
	}

	@Override
	public Usuario get(String usuario) throws Exception {
		Validador.idUsuario(usuario);
		return FRepo.get(usuarios, usuario, UsuarioNaoExisteException::new);
	}

	@Override
	public Map<String, Usuario> getLista() {
		return new LinkedHashMap<String, Usuario>(usuarios); 
	}
}
