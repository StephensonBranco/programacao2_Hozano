package br.ufal.ic.p2.bikeshare.servicos;

import java.time.Duration;
import java.time.LocalDateTime;

import br.ufal.ic.p2.bikeshare.exception.AluguelNaoEstaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exception.BateriaInsuficienteException;
import br.ufal.ic.p2.bikeshare.exception.EstacaoCheiaException;
import br.ufal.ic.p2.bikeshare.exception.EstacaoSemVagasException;
import br.ufal.ic.p2.bikeshare.exception.MetodoPagamentoNaoCadastradoException;
import br.ufal.ic.p2.bikeshare.exception.SaldoInsuficienteException;
import br.ufal.ic.p2.bikeshare.exception.UsuarioInadimplenteException;
import br.ufal.ic.p2.bikeshare.exception.UsuarioJaPossuiAluguelException;
import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.bicicleta.Eletrica;
import br.ufal.ic.p2.bikeshare.models.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioUsuarios;
import br.ufal.ic.p2.bikeshare.util.Formatador;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class CadastroAlugueis {
	private final RepositorioUsuarios repoUsuarios;
	private final RepositorioEstacoes repoEstacoes;
	private final RepositorioBicicletas repoBicicletas;
	private final RepositorioAlugueis repoAlugueis;
	
	public CadastroAlugueis(RepositorioUsuarios repoUsuarios, RepositorioEstacoes repoEstacoes, RepositorioBicicletas repoBicicletas, RepositorioAlugueis repoAlugueis) {
		this.repoUsuarios = repoUsuarios;
		this.repoEstacoes = repoEstacoes;
		this.repoBicicletas = repoBicicletas;
		this.repoAlugueis = repoAlugueis;
	}
	
	private void verificarCriarAluguel(Usuario usuario, Bicicleta bicicleta) throws Exception {
		if("eletrica".equals(bicicleta.getTipo()) && ((Eletrica)bicicleta).getNivelBateria() < 15)
			throw new BateriaInsuficienteException();
		
		if("nenhum".equals(usuario.getMetodoPagamento()))
			throw new MetodoPagamentoNaoCadastradoException();
		
		if("saldoPreCarregado".equals(usuario.getMetodoPagamento()) && usuario.getSaldo() == 0)
			throw new SaldoInsuficienteException();
		
		if(usuario.getAluguelAtivo() != null & !"nenhum".equals(usuario.getAluguelAtivo()))
			throw new UsuarioJaPossuiAluguelException();
		
		if("inadimplente".equals(usuario.getEstado()))
			throw new UsuarioInadimplenteException();
	}
	
	public String criarAluguel(String usuario, String estacao, String bicicleta, String data) throws Exception {
		Usuario user = repoUsuarios.get(usuario);
		Bicicleta bike = repoBicicletas.get(bicicleta);	
		Aluguel rent = new Aluguel(usuario, estacao, bicicleta, data);
		
		repoBicicletas.removerBicicletaDaEstacao(estacao, bicicleta);
		
		verificarCriarAluguel(user, bike);
		
		String id_aluguel = repoAlugueis.adicionar(rent);
		user.setAluguelAtivo(id_aluguel);
		user.setBicicleta(bicicleta);
		bike.setEstacao("nenhuma");
		bike.setEstado("emUso");
		
		return id_aluguel;
	}
	
	private void verificarFinalizacaoAluguel(Aluguel aluguel, Usuario usuario, Estacao estacao, LocalDateTime entrega) throws Exception{
		//Mesma ideia da Exception, porém outra mensagem
		try {
			estacao.adicionarBicicleta(aluguel.getBicicleta());
		} catch(EstacaoSemVagasException e) {
			throw new EstacaoCheiaException();
		}
		
		if(!"emAndamento".equals(aluguel.getEstado())) {
			throw new AluguelNaoEstaEmAndamentoException();
		}
		
		Validador.dataEmOrdem(aluguel.getInicio(), entrega);
	}
	
	///	Avulso:
	/// 	R$0,50/min convencional
	/// 	R$0,70/min eletrica
	/// Assinante:
	/// 	max(0, min-60)*R$0,30 convencional
	/// 	max(0, min-60)*R$0,30 + R$0,20/min elétrico
	public void finalizarAluguel(String aluguel, String estacao, String data) throws Exception {
		Aluguel rent = repoAlugueis.get(aluguel);
		Usuario user = repoUsuarios.get(rent.getUsuario());
		Estacao station = repoEstacoes.get(estacao);
		Bicicleta bike = repoBicicletas.get(rent.getBicicleta());
		LocalDateTime entrega = Formatador.stringToDate(data);		//
		Long minutos = Duration.between(rent.getInicio(), entrega).toMinutes();
		Double valor = 0.00;
	
		verificarFinalizacaoAluguel(rent, user, station, entrega);
		
		if(minutos > 1440) {		//Passou das 24 horas
			user.setEstado("inadimplente");
			user.setTotalPendencias(10.00);
			
			rent.setEstado("expirado");	
			
			bike.setEstado("emManutencao");
		}
		else if(minutos > 2 || !rent.getEstacaoOrigem().equals(estacao)) {	
			switch (user.getPlano()) {
				case "avulso" -> {
					if("convencional".equals(bike.getTipo())) {
						valor = minutos*0.50;
					}
					else {
						valor = minutos*0.70;
					}
				}
				case "assinante" -> {
					if("convencional".equals(bike.getTipo())) {
						valor = Math.max(0.00, minutos-60)*0.30;
					}
					else {
						valor = Math.max(0.00, minutos-60)*0.30 + minutos*0.20;
					}
				}
				default -> {
				
				}
			};
			
			//Caso não tenha o saldo necessário para pagar, o sistema aciona o erro. Caso tenha, é descontado.
			if("saldoPreCarregado".equals(user.getMetodoPagamento()) && valor > 0) {
				if(user.getSaldo() < valor)
					throw new SaldoInsuficienteException();
				else
					user.removerSaldo(valor);
			}
			
			rent.setEstado("finalizado");
			
			bike.setEstado("disponivel");
		}
		else{
			rent.setEstado("cancelado");	//se não passar de dois minutos, não é considerado
			
			bike.setEstado("disponivel");
		}
		
		user.setAluguelAtivo("nenhum");
		
		rent.setValorCobrado(valor);
		rent.setEstacaoDestino(estacao);
		rent.setFim(entrega);
		
		bike.setEstacao(estacao);
	}
}
