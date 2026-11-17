package br.ufal.ic.p2.bikeshare.Sistema;

import br.ufal.ic.p2.bikeshare.models.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.Usuario;

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
			default -> "1";
		};
	}
}
