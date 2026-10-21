package br.ufal.ic.p2.wepayu.sistema;

import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Comissionado;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Horista;

// Consultas (nao alteram estado)
public class ConsultaEmpregados {
	private final RepositorioEmpregados repo;

	public ConsultaEmpregados(RepositorioEmpregados repo) {
		this.repo = repo;
	}

	private static void exigirBanco(Empregado emp) throws Exception {
		if (!emp.getMetodoPagamento().equals("banco"))
			throw new NaoRecebeBancoException();
	}

	private static void exigirSindicalizado(Empregado emp) throws Exception {
		if (!emp.getSindicalizado())
			throw new EmpregadoNaoEhSindicalizadoException();
	}

	public String atributo(String emp, String atributo) throws Exception {
		Validador.atributo(atributo);
		Empregado empregado = repo.buscar(emp);

		switch (atributo) {
			case "nome":
				return empregado.getNome();
			case "endereco":
				return empregado.getEndereco();
			case "tipo":
				return empregado.getTipo();
			case "salario":
				return Formatador.decimal(empregado.getSalario());
			case "comissao":
				return Formatador.decimal(Validador.como(empregado, Comissionado.class,
						EmpregadoNaoEhComissionadoException::new).getComissao());
			case "metodoPagamento":
				return empregado.getMetodoPagamento();
			case "banco":
				exigirBanco(empregado);
				return empregado.getBanco();
			case "agencia":
				exigirBanco(empregado);
				return empregado.getAgencia();
			case "contaCorrente":
				exigirBanco(empregado);
				return empregado.getContaCorrente();
			case "idSindicato":
				exigirSindicalizado(empregado);
				return empregado.getIdSindicato();
			case "taxaSindical":
				exigirSindicalizado(empregado);
				return Formatador.decimal(empregado.getTaxaSindical());
			case "agendaPagamento":
				return empregado.getAgendaPagamento();
			default:
				return String.valueOf(empregado.getSindicalizado());
		}
	}

	// Se o nome for repetido, pega baseado no indice
	public String porNome(String nome, int indice) throws Exception {
		int idx = 0;
		for (Map.Entry<String, Empregado> entry : repo.todos().entrySet()) {
			if (entry.getValue().getNome().equals(nome) && ++idx == indice)
				return entry.getKey();
		}
		throw new NaoHaEmpregadoNomeException();
	}

	public String horasTrabalhadas(String emp, String dataInicial, String dataFinal, boolean extras) throws Exception {
		Horista horista = repo.buscar(emp, Horista.class, EmpregadoNaoEhHoristaException::new);
		Validador.Periodo p = Validador.periodo(dataInicial, dataFinal);
		return Formatador.horas(horista.horasTrabalhadas(p.inicio(), p.fim(), extras));
	}

	public String vendasRealizadas(String emp, String dataInicial, String dataFinal) throws Exception {
		Comissionado comissionado = repo.buscar(emp, Comissionado.class, EmpregadoNaoEhComissionadoException::new);
		Validador.Periodo p = Validador.periodo(dataInicial, dataFinal);
		return Formatador.decimal(comissionado.somarVendas(p.inicio(), p.fim()));
	}

	public String taxasServico(String emp, String dataInicial, String dataFinal) throws Exception {
		Empregado empregado = repo.buscar(emp);
		exigirSindicalizado(empregado);
		Validador.Periodo p = Validador.periodo(dataInicial, dataFinal);
		return Formatador.decimal(empregado.somarTaxasServico(p.inicio(), p.fim()));
	}
}