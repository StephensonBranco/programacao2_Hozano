package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class ValorNaoNumericoException extends Exception {
	public ValorNaoNumericoException(String atributo) {
		super(MontarMensagem(atributo));
	}
	
	private static String MontarMensagem(String atributo) {
		if(atributo.equals("Salario")) {
			return atributo + " deve ser numerico.";
		}
		else {
			return atributo + " deve ser numerica.";
		}
	}
}
