package br.ufal.ic.p2.bikeshare.models.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BicicletaNaoPossuiBateriaException;

public class Convencional extends Bicicleta {
	
	public Convencional() {
		
	}
	
	public Convencional(String tipo, String estacao) throws BicicletaNaoPossuiBateriaException {
		super(tipo, estacao);
	}

	@Override
	public boolean estaDisponivel() {
		return estado.equals("disponivel");
	}
}
