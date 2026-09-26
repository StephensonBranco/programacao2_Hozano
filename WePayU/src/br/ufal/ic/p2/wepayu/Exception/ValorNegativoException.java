package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class ValorNegativoException extends Exception {
	public ValorNegativoException(String atributo) {
		super(MontarMensagem(atributo));
	}
	
	private static String MontarMensagem(String atributo) {
		if(atributo.equals("Salario")) {
			return atributo + " deve ser nao-negativo.";
		}
		else {
			return atributo + " deve ser nao-negativa.";
		}
	}
}
