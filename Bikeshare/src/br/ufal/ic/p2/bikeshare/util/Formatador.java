package br.ufal.ic.p2.bikeshare.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

import br.ufal.ic.p2.bikeshare.exception.DataInvalidaException;

public class Formatador {
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/uuuu H:mm").withResolverStyle(ResolverStyle.STRICT);
	
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
	
	//Transformar String em Data
	public static LocalDateTime stringToDate(String string) throws DataInvalidaException {
		if(string == null || string.isEmpty()) {
			return null;
		}
		try {
			return LocalDateTime.parse(string, dtf);			
		} catch (DateTimeParseException e) {
			throw new DataInvalidaException();
		}
	}
	
	public static String dateToString(LocalDateTime data) {
		if(data == null) {
			return null;
		}
		return data.format(dtf);
	}
}
