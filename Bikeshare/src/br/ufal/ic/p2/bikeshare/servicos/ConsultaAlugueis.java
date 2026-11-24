package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.exception.AluguelEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exception.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.util.Formatador;

public class ConsultaAlugueis {
	private final RepositorioAlugueis repoAlugueis;
	
	public ConsultaAlugueis(RepositorioAlugueis repoAlugueis){
		this.repoAlugueis = repoAlugueis;
	}
	
	private void verificarEmAndamento(Aluguel aluguel) throws AluguelEmAndamentoException {
		if("emAndamento".equals(aluguel.getEstado()))
			throw new AluguelEmAndamentoException();	
	}
	
	public String getAtributo(String aluguel, String atributo) throws Exception{
		Aluguel rent = repoAlugueis.get(aluguel);
		return switch (atributo) {
			case "estado" -> rent.getEstado();
			case "usuario" -> rent.getUsuario();
			case "bicicleta" -> rent.getBicicleta();
			case "estacaoOrigem" -> rent.getEstacaoOrigem();
			case "inicio" -> Formatador.dateToString(rent.getInicio());
			case "valorCobrado" -> {
				verificarEmAndamento(rent);
				yield Formatador.doubleToString(rent.getValorCobrado());
			}
			case "fim" -> {
				verificarEmAndamento(rent);
				yield Formatador.dateToString(rent.getFim());
			}
			case "estacaoDestino" -> {
				verificarEmAndamento(rent);
				yield rent.getEstacaoDestino();
			}
			default -> throw new AtributoNaoExisteException();
		};
	}
}
