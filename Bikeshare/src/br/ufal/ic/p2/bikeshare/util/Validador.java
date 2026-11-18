package br.ufal.ic.p2.bikeshare.util;

import java.util.Map;
import java.util.function.Supplier;

import br.ufal.ic.p2.bikeshare.exception.CnpjNuloException;
import br.ufal.ic.p2.bikeshare.exception.CpfNuloException;
import br.ufal.ic.p2.bikeshare.exception.EmailInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.exception.EmailNuloException;
import br.ufal.ic.p2.bikeshare.exception.IdentificacaoNulaException;
import br.ufal.ic.p2.bikeshare.exception.MensalidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.exception.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.exception.MensalidadeNulaException;
import br.ufal.ic.p2.bikeshare.exception.NaoEhFisicaException;
import br.ufal.ic.p2.bikeshare.exception.NaoEhJuridicaException;
import br.ufal.ic.p2.bikeshare.exception.NaoPossuiMensalidadeException;
import br.ufal.ic.p2.bikeshare.exception.NaoUsaCartaoException;
import br.ufal.ic.p2.bikeshare.exception.NaoUsaPixException;
import br.ufal.ic.p2.bikeshare.exception.NaoUsaSaldoException;
import br.ufal.ic.p2.bikeshare.exception.NomeNuloException;
import br.ufal.ic.p2.bikeshare.exception.PlanoInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.PlanoNaoPossuiMensalidadeException;
import br.ufal.ic.p2.bikeshare.exception.RazaoSocialNulaException;
import br.ufal.ic.p2.bikeshare.exception.SenhaNulaException;
import br.ufal.ic.p2.bikeshare.exception.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.models.Usuario;

public class Validador {
	public Validador() {
		
	}
	
	public static void criacao(Map<String, Usuario> usuarios, String nome, String email, String senha, String tipo, String cadastro,
            String razaoSocial, String plano, String mensalidade) throws Exception{
		//Valores nulos
		if(nome.isEmpty() || nome.isBlank())
			throw new NomeNuloException();
		else if(email.isEmpty())
			throw new EmailNuloException();
		else if(senha.isEmpty() || senha.isBlank())
			throw new SenhaNulaException();
		else if(cadastro.isEmpty()) {
			if(tipo.equals("pessoaFisica"))
				throw new CpfNuloException();
			else
				throw new CnpjNuloException();
		}
		else if(tipo.equals("pessoaJuridica") && razaoSocial != null && razaoSocial.isEmpty())
			throw new RazaoSocialNulaException();
		
		//Valores invalidos
		else if(!email.endsWith(".com") || !email.contains("@"))
			throw new EmailInvalidoException();
		else if((!tipo.equals("pessoaFisica") && !tipo.equals("pessoaJuridica")) || (razaoSocial != null && tipo.equals("pessoaFisica")) || (razaoSocial == null && tipo.equals("pessoaJuridica")))
			throw new TipoInvalidoException();			
		else if(!plano.equals("avulso") && !plano.equals("assinante"))
			throw new PlanoInvalidoException();
		else if(!plano.equals("assinante") && mensalidade != null && !mensalidade.isEmpty())
			throw new PlanoNaoPossuiMensalidadeException();
		
		//Valores numericos invalidos
		else if(plano.equals("assinante")) {
			numerico(mensalidade, MensalidadeNulaException::new, MensalidadeNaoNumericaException::new, MensalidadeNegativaException::new);			
		}
		
		//Valores ja existentes
		else if(usuarios.values().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email))) {
			throw new EmailJaCadastradoException();
		}
	}
	
	public static void numerico(String valor, Supplier<? extends Exception> nulo, Supplier<? extends Exception> naoNumerico, Supplier<? extends Exception> negativo) throws Exception{
		if(valor == null || valor.isEmpty()) { 
			throw nulo.get();
		}
		else if(!valor.matches("-?\\d+(\\,\\d+)?")){
			throw naoNumerico.get();
		}
		else if(valor.charAt(0)=='-'){
			throw negativo.get();
		}
	}
	
	//Ver se o pagamento que está sendo requesitado do usuário é condizente com o metodoPagamento
	public static void pagamentoParaMetodo(Usuario usuario, String pagamento) throws Exception{
		//NOMES PROVISORIOS DE METODOS
		if((pagamento.equals("numeroCartao") || pagamento.equals("validadeCartao")) && !usuario.getMetodoPagamento().equals("Cartao"))
			throw new NaoUsaCartaoException();
		else if(pagamento.equals("chavePix") && !usuario.getMetodoPagamento().equals("Pix"))
			throw new NaoUsaPixException();
		else if(pagamento.equals("saldo") && !usuario.getMetodoPagamento().equals("Saldo"))
			throw new NaoUsaSaldoException();
	}
	
	public static void email(String email) throws Exception {
		if(email.isEmpty() || email.isBlank())
			throw new EmailNuloException();
	}
	
	public static void mensalidade(Usuario usuario) throws Exception{
		if(!usuario.getPlano().equals("assinante")) 
			throw new NaoPossuiMensalidadeException();
	}
	
	public static void id(String id) throws Exception{
		if(id.isEmpty())
			throw new IdentificacaoNulaException();
	}
	
	public static void pessoaFisica(Usuario usuario) throws Exception{
		if(!usuario.getTipo().equals("pessoaFisica"))
			throw new NaoEhFisicaException();
	}
	
	public static void pessoaJuridica(Usuario usuario) throws Exception{
		if(!usuario.getTipo().equals("pessoaJuridica"))
			throw new NaoEhJuridicaException();
	}
}
