package br.ufal.ic.p2.wepayu.util;

public final class Dinheiro {
	private Dinheiro() {}

	// Trunca (nao arredonda) em 2 casas decimais
	public static double truncar(double valor) {
		return Math.floor(valor * 100.0 + 1e-6) / 100.0;
	}
}