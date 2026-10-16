package br.ufal.ic.p2.wepayu.sistema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import br.ufal.ic.p2.wepayu.models.Assalariado;
import br.ufal.ic.p2.wepayu.models.Comissionado;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Horista;
import br.ufal.ic.p2.wepayu.models.Pagamento;
import br.ufal.ic.p2.wepayu.sistema.CalculadoraFolha.LinhaFolha;

public class RelatorioFolha {
	private static final Locale BR = Locale.of("pt", "BR");

	private static final String BORDA = "===============================================================================================================================";
	private static final String TITULO_HORISTAS = "===================== HORISTAS ================================================================================================";
	private static final String TITULO_ASSALARIADOS = "===================== ASSALARIADOS ============================================================================================";
	private static final String TITULO_COMISSIONADOS = "===================== COMISSIONADOS ===========================================================================================";

	private static final String COLUNAS_HORISTAS = "Nome                                 Horas Extra Salario Bruto Descontos Salario Liquido Metodo";
	private static final String SUBLINHADO_HORISTAS = "==================================== ===== ===== ============= ========= =============== ======================================";
	private static final String COLUNAS_ASSALARIADOS = "Nome                                             Salario Bruto Descontos Salario Liquido Metodo";
	private static final String SUBLINHADO_ASSALARIADOS = "================================================ ============= ========= =============== ======================================";
	private static final String COLUNAS_COMISSIONADOS = "Nome                  Fixo     Vendas   Comissao Salario Bruto Descontos Salario Liquido Metodo";
	private static final String SUBLINHADO_COMISSIONADOS = "===================== ======== ======== ======== ============= ========= =============== ======================================";

	public void escrever(File saida, LocalDate data, List<LinhaFolha> linhas) throws IOException {
		if (saida.getParentFile() != null)
			saida.getParentFile().mkdirs();

		try (PrintWriter w = new PrintWriter(new FileWriter(saida))) {
			w.println("FOLHA DE PAGAMENTO DO DIA " + data.format(DateTimeFormatter.ISO_LOCAL_DATE));
			w.println("====================================");
			w.println();

			double brutoHoristas = secaoHoristas(w, filtrar(linhas, Horista.class));
			double brutoAssalariados = secaoAssalariados(w, filtrar(linhas, Assalariado.class));
			double brutoComissionados = secaoComissionados(w, filtrar(linhas, Comissionado.class));

			// TOTAL GERAL (soma dos brutos, igual ao totalFolha)
			w.println(String.format(BR, "TOTAL FOLHA: %.2f", brutoHoristas + brutoAssalariados + brutoComissionados));
		}
	}

	private List<LinhaFolha> filtrar(List<LinhaFolha> linhas, Class<? extends Empregado> tipo) {
		return linhas.stream()
				.filter(l -> tipo.isInstance(l.empregado()))
				.sorted(Comparator.comparing((LinhaFolha l) -> l.empregado().getNome()))
				.toList();
	}

	private void cabecalho(PrintWriter w, String titulo, String colunas, String sublinhado) {
		w.println(BORDA);
		w.println(titulo);
		w.println(BORDA);
		w.println(colunas);
		w.println(sublinhado);
	}

	// cada secao devolve o total bruto para o TOTAL FOLHA
	private double secaoHoristas(PrintWriter w, List<LinhaFolha> linhas) {
		cabecalho(w, TITULO_HORISTAS, COLUNAS_HORISTAS, SUBLINHADO_HORISTAS);
		double horasN = 0, horasE = 0, bruto = 0, descontos = 0, liquido = 0;

		for (LinhaFolha l : linhas) {
			Pagamento p = l.pagamento();
			horasN += p.horasNormais();
			horasE += p.horasExtras();
			bruto += p.bruto();
			descontos += l.descontos();
			liquido += l.liquido();

			w.println(String.format(BR, "%-36s %5s %5s %13.2f %9.2f %15.2f %s",
					l.empregado().getNome(), Formatador.horas(p.horasNormais()), Formatador.horas(p.horasExtras()),
					p.bruto(), l.descontos(), l.liquido(), Formatador.metodoPagamento(l.empregado())));
		}
		w.println();
		w.println(String.format(BR, "%-36s %5s %5s %13.2f %9.2f %15.2f",
				"TOTAL HORISTAS", Formatador.horas(horasN), Formatador.horas(horasE), bruto, descontos, liquido));
		w.println();
		return bruto;
	}

	private double secaoAssalariados(PrintWriter w, List<LinhaFolha> linhas) {
		cabecalho(w, TITULO_ASSALARIADOS, COLUNAS_ASSALARIADOS, SUBLINHADO_ASSALARIADOS);
		double bruto = 0, descontos = 0, liquido = 0;

		for (LinhaFolha l : linhas) {
			bruto += l.pagamento().bruto();
			descontos += l.descontos();
			liquido += l.liquido();

			w.println(String.format(BR, "%-48s %13.2f %9.2f %15.2f %s",
					l.empregado().getNome(), l.pagamento().bruto(), l.descontos(), l.liquido(),
					Formatador.metodoPagamento(l.empregado())));
		}
		w.println();
		w.println(String.format(BR, "%-48s %13.2f %9.2f %15.2f", "TOTAL ASSALARIADOS", bruto, descontos, liquido));
		w.println();
		return bruto;
	}

	private double secaoComissionados(PrintWriter w, List<LinhaFolha> linhas) {
		cabecalho(w, TITULO_COMISSIONADOS, COLUNAS_COMISSIONADOS, SUBLINHADO_COMISSIONADOS);
		double fixo = 0, vendas = 0, comissao = 0, bruto = 0, descontos = 0, liquido = 0;

		for (LinhaFolha l : linhas) {
			Pagamento p = l.pagamento();
			fixo += p.fixo();
			vendas += p.vendas();
			comissao += p.comissao();
			bruto += p.bruto();
			descontos += l.descontos();
			liquido += l.liquido();

			w.println(String.format(BR, "%-21s %8.2f %8.2f %8.2f %13.2f %9.2f %15.2f %s",
					l.empregado().getNome(), p.fixo(), p.vendas(), p.comissao(), p.bruto(),
					l.descontos(), l.liquido(), Formatador.metodoPagamento(l.empregado())));
		}
		w.println();
		w.println(String.format(BR, "%-21s %8.2f %8.2f %8.2f %13.2f %9.2f %15.2f",
				"TOTAL COMISSIONADOS", fixo, vendas, comissao, bruto, descontos, liquido));
		w.println();
		return bruto;
	}
}