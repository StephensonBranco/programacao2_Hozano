package br.ufal.ic.p2.bikeshare.servicos;

import java.util.Map;

import br.ufal.ic.p2.bikeshare.exception.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Cartao;
import br.ufal.ic.p2.bikeshare.models.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.Usuario;
import br.ufal.ic.p2.bikeshare.util.Formatador;
import br.ufal.ic.p2.bikeshare.util.Validador;
import br.ufal.ic.p2.bikeshare.exception.*;

public class ConsultaUsuarios {
	private final Repositorio repo; 
	public ConsultaUsuarios(Repositorio repo) {
		this.repo = repo;
	}
	
	public String getAtributoUsuario(String id, String atributo) throws Exception{
		Usuario usuario = repo.getUsuario(id);
		
		return switch (atributo) {
			case "nome" -> usuario.getNome();
			case "email" -> usuario.getEmail();
			case "senha" -> usuario.getSenha();
			case "tipo" -> usuario.getTipo();
			case "estado" -> usuario.getEstado();
			case "plano" -> usuario.getPlano();
			case "mensalidade" ->  {
				if(!usuario.getPlano().equals("assinante"))
					throw new PlanoNaoPossuiMensalidadeException();
				yield Formatador.doubleToString(usuario.getMensalidade());
			}
			case "cpf" -> {
				Validador.pessoaFisica(usuario);		//caso não seja pessoaFisica
				yield usuario.getCadastro();
			}
			case "cnpj" -> {
				Validador.pessoaJuridica(usuario);		//caso não seja pessoaJuridica
				yield usuario.getCadastro();
			}
			case "razaoSocial" -> {
				Validador.pessoaJuridica(usuario);
				yield ((PessoaJuridica)usuario).getRazaoSocial();
			}
			case "metodoPagamento" -> usuario.getMetodoPagamento();
			case "aluguelAtivo" -> usuario.getAluguelAtivo();
			case "totalPendencias" -> Formatador.doubleToString(usuario.getTotalPendencias());
			case "numeroCartao" -> {
				Validador.pagamentoParaMetodo(usuario, atributo);
				Cartao cartao = usuario.getCartao();
				yield cartao.getNumeroCartao();
			}
			case "validadeCartao" -> {
				Validador.pagamentoParaMetodo(usuario, atributo);
				Cartao cartao = usuario.getCartao();
				yield cartao.getValidadeCartao();
			}
			case "chavePix" -> {
				Validador.pagamentoParaMetodo(usuario, atributo);
				yield usuario.getChavePix();
			}
			case "saldo" -> {
				Validador.pagamentoParaMetodo(usuario, atributo);
				yield Formatador.doubleToString(usuario.getSaldo());
			}
			default -> throw new AtributoNaoExisteException();
		};
	}
	
	public String getUsuarioPorEmail(String email) throws Exception{
		Validador.nulo(email, EmailNuloException::new);
		
		Map<String, Usuario> usuarios = repo.getListaUsuarios();
		String id = usuarios.
				entrySet().
				stream().
				filter(e -> e.getValue().getEmail().equalsIgnoreCase(email)).
				map(Map.Entry::getKey).
				findFirst().
				orElseThrow(UsuarioNaoExisteException::new);
		return id;	
	}
}
