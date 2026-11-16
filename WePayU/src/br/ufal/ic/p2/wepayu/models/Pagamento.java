package br.ufal.ic.p2.wepayu.models;

public record Pagamento(double horasNormais, double horasExtras, double fixo,
		double vendas, double comissao, double bruto) {

	public static Pagamento apenasBruto(double bruto) {
		return new Pagamento(0, 0, 0, 0, 0, bruto);
	}
}