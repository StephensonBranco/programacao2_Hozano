package br.ufal.ic.p2.bikeshare.models.bicicleta;

import br.ufal.ic.p2.bikeshare.util.Validador;

public class Eletrica extends Bicicleta {
	private int nivelBateria;
	public Eletrica() {
		
	}
	
	public Eletrica(String tipo, String nivelBateria, String estacao) throws Exception {
		super(tipo, estacao);
		setNivelBateria(nivelBateria);
	}

	public int getNivelBateria() {
		return nivelBateria;
	}

	public void setNivelBateria(String nivelBateria) throws Exception {
		Validador.nivelBateria(nivelBateria);
		this.nivelBateria = Integer.parseInt(nivelBateria);
	}
	
	//XML
	public void setNivelBateria(int nivelBateria) {
		this.nivelBateria = nivelBateria;
	}

	@Override
	public boolean estaDisponivel() {
		return (estado.equals("disponivel")) && (nivelBateria > 0);
	}
	
}
