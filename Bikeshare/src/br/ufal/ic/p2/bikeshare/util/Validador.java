package br.ufal.ic.p2.bikeshare.util;

import java.util.Map;
import java.util.function.Supplier;
import java.util.List;

import br.ufal.ic.p2.bikeshare.exception.CapacidadeNaoNumerica;
import br.ufal.ic.p2.bikeshare.exception.CapacidadeNegativaException;
import br.ufal.ic.p2.bikeshare.exception.CapacidadeNulaException;
import br.ufal.ic.p2.bikeshare.exception.ChavePixNulaException;
import br.ufal.ic.p2.bikeshare.exception.CnpjNuloException;
import br.ufal.ic.p2.bikeshare.exception.CpfNuloException;
import br.ufal.ic.p2.bikeshare.exception.EmailInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.exception.EmailNuloException;
import br.ufal.ic.p2.bikeshare.exception.EnderecoNuloException;
import br.ufal.ic.p2.bikeshare.exception.IdentificacaoEstacaoNulaException;
import br.ufal.ic.p2.bikeshare.exception.IdentificacaoUsuarioNulaException;
import br.ufal.ic.p2.bikeshare.exception.MensalidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.exception.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.exception.MensalidadeNulaException;
import br.ufal.ic.p2.bikeshare.exception.MetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.NaoEhFisicaException;
import br.ufal.ic.p2.bikeshare.exception.NaoEhJuridicaException;
import br.ufal.ic.p2.bikeshare.exception.NaoUsaCartaoException;
import br.ufal.ic.p2.bikeshare.exception.NaoUsaPixException;
import br.ufal.ic.p2.bikeshare.exception.NaoUsaSaldoException;
import br.ufal.ic.p2.bikeshare.exception.NomeNuloException;
import br.ufal.ic.p2.bikeshare.exception.NumeroCartaoNuloException;
import br.ufal.ic.p2.bikeshare.exception.PlanoInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.PlanoNaoPossuiMensalidadeException;
import br.ufal.ic.p2.bikeshare.exception.RazaoSocialNulaException;
import br.ufal.ic.p2.bikeshare.exception.SaldoNaoNumericoException;
import br.ufal.ic.p2.bikeshare.exception.SaldoNegativoException;
import br.ufal.ic.p2.bikeshare.exception.SaldoNuloException;
import br.ufal.ic.p2.bikeshare.exception.SenhaNulaException;
import br.ufal.ic.p2.bikeshare.exception.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.ValidadeCartaoNulaException;
import br.ufal.ic.p2.bikeshare.exception.ValidadeCartaoInvalidaException;
import br.ufal.ic.p2.bikeshare.models.Usuario;

public class Validador {
	public Validador() {
		
	}
	
	//Validadores de numeros
	public static void naoNegativo(String valor, Supplier<? extends Exception> negativo) throws Exception{
		if(valor.charAt(0)=='-'){
			throw negativo.get();
		}
	}
	
	public static void positivo(String valor, Supplier<? extends Exception> naoPositivo) throws Exception {
		if(valor.charAt(0)=='-' || valor.charAt(0)=='0'){
			throw naoPositivo.get();
		}
	}
	
	public static void numerico(String valor, Supplier<? extends Exception> naoNumerico) throws Exception{
		if(!valor.matches("-?\\d+(\\,\\d+)?"))
			throw naoNumerico.get();
	}
	//
	
	//Packs de validação para numeros
	public static void numericoPositivo(String valor, Supplier<? extends Exception> naoNumerico, Supplier<? extends Exception> naoPositivo) throws Exception{
		Validador.numerico(valor, naoNumerico);
		Validador.positivo(valor, naoPositivo);
	}
	
	public static void numericoNaoNegativo(String valor, Supplier<? extends Exception> naoNumerico, Supplier<? extends Exception> negativo) throws Exception{
		Validador.numerico(valor, naoNumerico);
		Validador.naoNegativo(valor, negativo);
	}
	//
	
	public static void nulo(String atributo, Supplier<? extends Exception> e) throws Exception{
		if(atributo == null || atributo.isEmpty() || atributo.isBlank())
			throw e.get();
	}
	
	//Ver se o pagamento que está sendo requesitado do usuário é condizente com o metodoPagamento
	public static void pagamentoParaMetodo(Usuario usuario, String pagamento) throws Exception{
		//NOMES PROVISORIOS DE METODOS
		if((pagamento.equals("numeroCartao") || pagamento.equals("validadeCartao")) && !usuario.getMetodoPagamento().equals("cartao"))
			throw new NaoUsaCartaoException();
		else if(pagamento.equals("chavePix") && !usuario.getMetodoPagamento().equals("pix"))
			throw new NaoUsaPixException();
		else if(pagamento.equals("saldo") && !usuario.getMetodoPagamento().equals("saldoPreCarregado"))
			throw new NaoUsaSaldoException();
	}
	
	public static void emailRepetido(Map<String, Usuario> usuarios, String email) throws EmailJaCadastradoException{
		if(usuarios.values().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email)))
			throw new EmailJaCadastradoException();
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
	
	public static void idUsuario(String id) throws Exception{
		Validador.nulo(id, IdentificacaoUsuarioNulaException::new);
	}
	
	public static void plano(String plano) throws Exception {
		if(!plano.equals("avulso") && !plano.equals("assinante"))
			throw new PlanoInvalidoException();
	}
	
	public static void mensalidade(String mensalidade, String plano) throws Exception{
		if(plano.equals("assinante")) {
			Validador.nulo(mensalidade, MensalidadeNulaException::new);
			Validador.numericoNaoNegativo(mensalidade, MensalidadeNaoNumericaException::new, MensalidadeNegativaException::new);
		}
		else{
			if(mensalidade != null && !mensalidade.isEmpty())
				throw new PlanoNaoPossuiMensalidadeException();
		}
	}
	
	public static void metodoPagamento(String metodoPagamento) throws Exception {
		if(!List.of("cartao", "pix", "saldoPreCarregado", "nenhum").contains(metodoPagamento))
			throw new MetodoPagamentoInvalidoException();
	}
	
	public static void numeroCartao(String numeroCartao) throws Exception {
		Validador.nulo(numeroCartao, NumeroCartaoNuloException::new);
	}
	
	public static void validadeCartao(String validadeCartao) throws Exception {
		Validador.nulo(validadeCartao, ValidadeCartaoNulaException::new);
		if(!validadeCartao.matches("\\d{2}/\\d{4}"))
			throw new ValidadeCartaoInvalidaException();
			
		String[] partes = validadeCartao.split("/");
		
		int mes = Integer.parseInt(partes[0]);
		
		if(mes < 1 || mes > 12)
			throw new ValidadeCartaoInvalidaException();
	}
	
	public static void chavePix(String chavePix) throws Exception {
		if(chavePix!=null)
			Validador.nulo(chavePix, ChavePixNulaException::new);
	}
	
	public static void saldo(String saldo) throws Exception {	
		Validador.nulo(saldo, SaldoNuloException::new);
		Validador.numericoPositivo(saldo, SaldoNaoNumericoException::new, SaldoNegativoException::new);
	}
	
	public static void endereco(String endereco) throws Exception {
		Validador.nulo(endereco, EnderecoNuloException::new);
	}
	
	public static void capacidade(String capacidade) throws Exception {
		Validador.nulo(capacidade, CapacidadeNulaException::new);
		if(!capacidade.matches("-?\\d+"))
			throw new CapacidadeNaoNumerica();
		Validador.positivo(capacidade, CapacidadeNegativaException::new);
	}
	
	public static void idEstacao(String id) throws Exception{
		Validador.nulo(id, IdentificacaoEstacaoNulaException::new);
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
