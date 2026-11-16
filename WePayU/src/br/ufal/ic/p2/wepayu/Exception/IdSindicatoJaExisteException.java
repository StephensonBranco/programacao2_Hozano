package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class IdSindicatoJaExisteException extends Exception {
	public IdSindicatoJaExisteException() {
		super("Ha outro empregado com esta identificacao de sindicato");
	}
}
