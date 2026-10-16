package br.ufal.ic.p2.wepayu.sistema;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import br.ufal.ic.p2.wepayu.models.Empregado;

public final class Formatador {
	private static final Locale BR = Locale.of("pt", "BR");

	private Formatador() {}

	// 2 casas com virgula (ex.: 1000,00)
	public static String decimal(double valor) {
		return String.format(BR, "%.2f", valor);
	}

	public static String valor(double valor) {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator(',');
		simbolos.setGroupingSeparator('.');
		return new DecimalFormat("0.00", simbolos).format(valor);
	}

	// Horas sem casas quando inteiras (15), com virgula quando fracionadas (7,5)
	public static String horas(double horas) {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator(',');
		return new DecimalFormat("0.###", simbolos).format(horas);
	}

	public static String metodoPagamento(Empregado emp) {
		String metodo = emp.getMetodoPagamento();
		if ("emMaos".equalsIgnoreCase(metodo)) {
			return "Em maos";
		} else if ("banco".equalsIgnoreCase(metodo)) {
			return String.format("%s, Ag. %s CC %s", emp.getBanco(), emp.getAgencia(), emp.getContaCorrente());
		} else if ("correios".equalsIgnoreCase(metodo)) {
			return String.format("Correios, %s", emp.getEndereco());
		}
		return metodo;
	}
}