package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.repositorio.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioUsuarios;

public class Sistema {
	private final RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
	private final RepositorioEstacoes repoEstacoes = new RepositorioEstacoes();
	private final RepositorioBicicletas repoBicicletas= new RepositorioBicicletas(repoEstacoes);
	private final RepositorioAlugueis repoAlugueis= new RepositorioAlugueis();

	private final ConsultaUsuarios consultaUsuarios = new ConsultaUsuarios(repoUsuarios);
	private final ConsultaBicicletas consultaBicicletas = new ConsultaBicicletas(repoBicicletas);
	private final ConsultaEstacoes consultaEstacoes  = new ConsultaEstacoes(repoEstacoes, repoBicicletas);
	private final ConsultaAlugueis consultaAlugueis  = new ConsultaAlugueis(repoAlugueis);
	private final CadastroUsuarios cadastroUsuarios = new CadastroUsuarios(repoUsuarios);
	private final CadastroBicicletas cadastroBicicletas = new CadastroBicicletas(repoBicicletas);
	private final CadastroEstacoes cadastroEstacoes = new CadastroEstacoes(repoEstacoes);
	private final CadastroAlugueis cadastroAlugueis = new CadastroAlugueis(repoUsuarios, repoEstacoes, repoBicicletas, repoAlugueis);
	
	/////////////
	///Alteram o sistema
	public String criarUsuario(String nome, String email, String senha, String tipo, String cadastro,
            String razaoSocial, String plano, String mensalidade) throws Exception{
		return cadastroUsuarios.criarUsuario(nome, email, senha, tipo, cadastro, razaoSocial, plano, mensalidade);
	}
	
	public void alterarUsuario(String usuario, String atributo, String valor) throws Exception {
		cadastroUsuarios.alterarUsuario(usuario, atributo, valor);
	}
	
	public void alterarUsuario(String usuario, String atributo, String valor1, String valor2) throws Exception {
		cadastroUsuarios.alterarUsuario(usuario, atributo, valor1, valor2);
	}
	
	public void alterarUsuario(String usuario, String atributo, String valor, String numeroCartao, String validadeCartao) throws Exception {
		cadastroUsuarios.alterarUsuario(usuario, atributo, valor, numeroCartao, validadeCartao);
	}
	
	public void removerUsuario(String usuario) throws Exception {
		repoUsuarios.remover(usuario);
	}
	
	public String criarEstacao(String nome, String endereco, String capacidade) throws Exception {
		return cadastroEstacoes.criarEstacao(nome, endereco, capacidade);
	}
	
	public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) throws Exception {
		cadastroBicicletas.criarBicicleta(estacao, bicicleta, tipo, nivelBateria);
	}
	
	public void removerBicicleta(String estacao, String bicicleta) throws Exception{
		repoBicicletas.remover(estacao, bicicleta);
	}
	
	public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data) throws Exception{
		return cadastroAlugueis.criarAluguel(usuario, estacao, bicicleta, data);	//Quando retira uma bicicleta, está fazendo um aluguel
	}
	
	public void devolverBicicleta(String aluguel, String estacao, String data) throws Exception {
		cadastroAlugueis.finalizarAluguel(aluguel, estacao, data);
	}
	
	public void zerarSistema() {
		repoUsuarios.zerar();
		repoEstacoes.zerar();
		repoBicicletas.zerar();
		repoAlugueis.zerar();
	}
	
	
	
	/////////////
	//Não alteram o sistema
	public String getAtributoUsuario(String Usuario, String atributo) throws Exception{
		return consultaUsuarios.getAtributo(Usuario, atributo);
	}
	
	public String getUsuarioPorEmail(String email) throws Exception{
		return consultaUsuarios.getUsuarioPorEmail(email);
	}
//	
	public String getVagasDisponiveis(String estacao) throws Exception {
		return consultaEstacoes.getVagasDisponiveis(estacao);
	}
	
	public String getBicicletasDisponiveis(String estacao) throws Exception {
		return consultaEstacoes.getBicicletasDisponiveis(estacao);
	}
	
	public String getAtributoBicicleta(String bicicleta, String atributo) throws Exception {
		return consultaBicicletas.getAtributo(bicicleta, atributo);
	}
	
	public String getEstadoBicicleta(String bicicleta) throws Exception {
		return consultaBicicletas.getAtributo(bicicleta, "estado");
	}
	
	public String getAtributoAluguel(String aluguel, String atributo) throws Exception {
		return consultaAlugueis.getAtributo(aluguel, atributo);
	}
	
	public void encerrarSistema() {
		repoUsuarios.salvar();
		repoEstacoes.salvar();
		repoBicicletas.salvar();
		repoAlugueis.salvar();
	}
}
