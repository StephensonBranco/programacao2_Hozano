package br.ufal.ic.p2.bikeshare.util;

import java.util.Locale;

public class Formatador {
	//Transformar String em Double
	public static Double stringToDouble(String string) {
		if(string == null || string.isEmpty())
			return null;
		return Double.parseDouble(string.replace(',', '.'));
	}
	
	//Transformar Double em String
	public static String doubleToString(Double valor) {
		if(valor == null) {
			return null;			
		}
		return String.format(Locale.of("pt", "BR"), "%.2f", valor);
	}
}
