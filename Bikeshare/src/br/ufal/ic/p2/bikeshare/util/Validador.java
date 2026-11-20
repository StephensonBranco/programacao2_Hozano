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
	
	public static void numerico(String valor, Supplier<? extends Exception> naoNumerico, Supplier<? extends Exception> negativo) throws Exception{
		if(!valor.matches("-?\\d+(\\,\\d+)?")){
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
	
	public static void emailRepetido(Map<String, Usuario> usuarios, String email) throws Exception{
		if(usuarios.values().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email))) {
			throw new EmailJaCadastradoException();
		}
	}
	
	public static void nulo(String atributo, Supplier<? extends Exception> e) throws Exception{
		if(atributo == null || atributo.isEmpty() || atributo.isBlank())
			throw e.get();
	}
	
	public static void nome(String nome) throws Exception {
		Validador.nulo(nome, NomeNuloException::new);
	}
	
	public static void email(String email) throws Exception{
		Validador.nulo(email, EmailNuloException::new);
		if(!email.endsWith(".com") || !email.contains("@"))
			throw new EmailInvalidoException();
	}
	
	public static void senha(String senha) throws Exception{
		Validador.nulo(senha, SenhaNulaException::new);
	}
	
	public static void cpf(String cpf) throws Exception {
		Validador.nulo(cpf, CpfNuloException::new);
	}
	
	public static void cnpj(String cnpj) throws Exception {	
		Validador.nulo(cnpj, CnpjNuloException::new);
	}
	
	public static void razaoSocial(String razaoSocial) throws Exception {
		if(razaoSocial==null)
			throw new TipoInvalidoException();
		Validador.nulo(razaoSocial, RazaoSocialNulaException::new);
	}
	
	public static void id(String id) throws Exception{
		Validador.nulo(id, IdentificacaoNulaException::new);
	}
	
	public static void plano(String plano) throws Exception {
		if(!plano.equals("avulso") && !plano.equals("assinante"))
			throw new PlanoInvalidoException();
	}
	
	public static void mensalidade(String mensalidade, String plano) throws Exception{
		if(plano.equals("assinante")) {
			Validador.nulo(mensalidade, MensalidadeNulaException::new);
			Validador.numerico(mensalidade, MensalidadeNaoNumericaException::new, MensalidadeNegativaException::new);
		}
		else {
			if(mensalidade != null && !mensalidade.isEmpty())
				throw new PlanoNaoPossuiMensalidadeException();
		}
		
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
