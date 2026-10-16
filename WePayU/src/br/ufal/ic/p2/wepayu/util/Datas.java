package br.ufal.ic.p2.wepayu.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Map;

public final class Datas {
	public static final DateTimeFormatter FORMATO = new DateTimeFormatterBuilder()
			.appendPattern("d/M/uuuu")
			.toFormatter()
			.withResolverStyle(ResolverStyle.STRICT);

	private Datas() {}

	public static LocalDate parse(String chave) {
		return LocalDate.parse(chave, FORMATO);
	}

	// Soma os valores do mapa (data -> valor) no intervalo [inicio, fim)
	public static double somarNoIntervalo(Map<String, Double> mapa, LocalDate inicio, LocalDate fim) {
		double soma = 0.0;
		for (Map.Entry<String, Double> entry : mapa.entrySet()) {
			LocalDate d = parse(entry.getKey());
			if (!d.isBefore(inicio) && d.isBefore(fim))
				soma += entry.getValue();
		}
		return soma;
	}
}