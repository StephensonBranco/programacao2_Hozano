package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class NuloException extends Exception {
	public NuloException(String atributo) {
		super(montarMensagem(atributo));
	}
	
	private static String montarMensagem(String atributo) {
		if(atributo.equals("Comissao") || atributo.equals("Identificacao do empregado")) {
			return atributo + " nao pode ser nula.";
		}
		else {
			return atributo + " nao pode ser nulo.";
		}
	}
}
