package br.ufal.ic.p2.bikeshare.exception;

@SuppressWarnings("serial")
public class SenhaNulaException extends Exception {
	public SenhaNulaException(){
		super("Senha nao pode ser nula.");		
	}
}
