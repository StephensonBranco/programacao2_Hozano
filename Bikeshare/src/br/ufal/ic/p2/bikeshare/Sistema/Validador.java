package br.ufal.ic.p2.bikeshare.Sistema;

import br.ufal.ic.p2.bikeshare.exception.NaoEhFisicaException;
import br.ufal.ic.p2.bikeshare.exception.NaoEhJuridicaException;
import br.ufal.ic.p2.bikeshare.exception.NaoEhTipoException;
import br.ufal.ic.p2.bikeshare.models.Usuario;

public class Validador {
	public Validador() {
		
	}
	
	public static void pessoaFisica(Usuario usuario) throws Exception{
		if(!usuario.getTipo().equals("pessoaFisica"))
			throw new NaoEhFisicaException();
	}
	
	public static void pessoaJuridica(Usuario usuario) throws Exception{
		if(!usuario.getTipo().equals("pessoaJuridica"))
			throw new NaoEhJuridicaException();
	}
}
