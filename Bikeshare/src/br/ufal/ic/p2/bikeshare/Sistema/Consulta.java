package br.ufal.ic.p2.bikeshare.Sistema;

import br.ufal.ic.p2.bikeshare.exception.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.IdentificacaoNulaException;
import br.ufal.ic.p2.bikeshare.models.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.Usuario;
import br.ufal.ic.p2.bikeshare.util.Formatador;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class Consulta {
	private final Repositorio repo; 
	public Consulta(Repositorio repo) {
		this.repo = repo;
	}
	
	public String getAtributoUsuario(String id, String atributo) throws Exception{
		Usuario usuario = repo.getUsuario(id);
		
		return switch (atributo) {
			case "nome" -> usuario.getNome();
			case "email" -> usuario.getEmail();
			case "tipo" -> usuario.getTipo();
			case "estado" -> usuario.getEstado();
			case "plano" -> usuario.getPlano();
			case "mensalidade" -> Validador.mensalidade(usuario);
			case "cpf" -> {
				Validador.pessoaFisica(usuario);
				yield usuario.getCadastro();
			}
			case "cnpj" -> {
				Validador.pessoaJuridica(usuario);
				yield usuario.getCadastro();
			}
			case "razaoSocial" -> {
				Validador.pessoaJuridica(usuario);
				yield ((PessoaJuridica)usuario).getRazaoSocial()	;
			}
			case "metodoPagamento" -> usuario.getMetodoPagamento();
			default -> throw new AtributoNaoExisteException();
		};
	}
}
