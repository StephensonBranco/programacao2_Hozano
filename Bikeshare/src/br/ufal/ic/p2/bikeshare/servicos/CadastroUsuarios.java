package br.ufal.ic.p2.bikeshare.servicos;

import java.util.List;
import java.util.Map;

import br.ufal.ic.p2.bikeshare.exception.AtributoNaoPodeSerAlteradoException;
import br.ufal.ic.p2.bikeshare.exception.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.exception.MetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.SaldoNaoNumericoException;
import br.ufal.ic.p2.bikeshare.exception.SaldoNegativoException;
import br.ufal.ic.p2.bikeshare.exception.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.models.usuario.PessoaFisica;
import br.ufal.ic.p2.bikeshare.models.usuario.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.models.Cartao;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class CadastroUsuarios {
	private final Repositorio repo;
	public CadastroUsuarios(Repositorio repo) {
		this.repo = repo;
	}
	
	public String criarUsuario(String nome, String email, String senha, String tipo, String cadastro,
								String razaoSocial, String plano, String mensalidade) throws Exception {
		
		Usuario usuario;
		
		//Valores padrões
		if(plano==null)
			plano = "avulso";
		
		//procurando tipos repetidos
		Validador.emailRepetido(repo.getListaUsuarios(), email);
		
		switch (tipo) {
			case "pessoaFisica":
				if(razaoSocial!=null)
					throw new TipoInvalidoException();
				usuario = new PessoaFisica(nome, email, senha, tipo, cadastro, plano, mensalidade);
				break;
			case "pessoaJuridica":
				usuario = new PessoaJuridica(nome, email, senha, tipo, cadastro, razaoSocial, plano, mensalidade);
				break;
			default:
				throw new TipoInvalidoException();
		}
		
		String id = repo.adicionarUsuario(usuario);
		return id;
	}
	
	//s1 = valor
	//s1 = metodoPagamento; s2 = chave
	//s1 = metodoPagamento; s2 = numero; s3 = validade
	
	public void alterarUsuario(String usuario, String atributo, String valor) throws Exception {
		Usuario user = repo.getUsuario(usuario);
		switch (atributo) {
			case "nome" -> {
				user.setNome(valor);
				break;
			}
			case "email" -> {
				//Usuario pode alterar para o mesmo email, precisando checar se o email repetido nos usuários não é o dele
				
				Map<String, Usuario> usuarios= repo.getListaUsuarios();
				try {
					Validador.emailRepetido(usuarios, valor);
					user.setEmail(valor);
				} catch(EmailJaCadastradoException e) {
					if(!user.getEmail().equalsIgnoreCase(valor))
						throw e;						
				}
				break;
			}
			case "senha" -> user.setSenha(valor);
			case "plano" -> {
				if("assinante".equals(valor) && "avulso".equals(user.getPlano()))
					user.setMensalidade(30.0);
				user.setPlano(valor);
				break;
			}
			case "metodoPagamento" -> {
				if(List.of("saldoPreCarregado","nenhum").contains(valor)) {
					setarPagamento(user, null, null, valor);
					
				}
				else
					throw new MetodoPagamentoInvalidoException();
			}
			case "saldo" -> {
				Validador.pagamentoParaMetodo(user, atributo);
				user.setSaldo(valor);
				break;
			}
			default -> throw new AtributoNaoPodeSerAlteradoException();
		}
	}
	
	//Saldo só altera se for adicionar mais
	private void setarPagamento(Usuario user, Cartao cartao, String chavePix, String metodoPagamento) throws Exception {
		user.setCartao(cartao);
		user.setChavePix(chavePix);
		user.setMetodoPagamento(metodoPagamento);
	}
	
	//valor = chavePix ou Saldo
	public void alterarUsuario(String usuario, String atributo, String metodoPagamento, String valor) throws Exception {
		Usuario user = repo.getUsuario(usuario);
		switch (atributo) {
			case "metodoPagamento" -> {
				if("pix".equals(metodoPagamento)) {
					setarPagamento(user, null, valor, metodoPagamento);
				}
				else if("saldoPreCarregado".equals(metodoPagamento))
					try {
						user.setSaldo(valor);
						setarPagamento(user, null, null, metodoPagamento);
					} catch (SaldoNaoNumericoException | SaldoNegativoException e) {
						throw new MetodoPagamentoInvalidoException();
					}
				else
					throw new MetodoPagamentoInvalidoException();
				break;
			}
			default -> throw new AtributoNaoPodeSerAlteradoException();
		}
 	}
	
	public void alterarUsuario(String usuario, String atributo, String metodoPagamento, String numeroCartao, String validadeCartao) throws Exception {
		Usuario user = repo.getUsuario(usuario);
		switch (atributo) {
			case "metodoPagamento" -> {
				if("cartao".equals(metodoPagamento)) {
					setarPagamento(user, new Cartao(numeroCartao, validadeCartao), null, metodoPagamento);
				}
				else
					throw new MetodoPagamentoInvalidoException();
				break;
			}
			default -> throw new AtributoNaoPodeSerAlteradoException();
		}
 	}
}
