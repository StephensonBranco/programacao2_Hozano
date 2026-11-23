package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.repositorio.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioUsuarios;

public class Sistema {
	private final RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
	private final RepositorioEstacoes repoEstacoes = new RepositorioEstacoes();
	private final RepositorioBicicletas repoBicicletas= new RepositorioBicicletas(repoEstacoes);
	private final RepositorioAlugueis repoAlugueis= new RepositorioAlugueis(repoUsuarios, repoBicicletas);

	private final CadastroUsuarios cadastroUsuarios = new CadastroUsuarios(repoUsuarios);
	private final CadastroBicicletas cadastroBicicletas = new CadastroBicicletas(repoBicicletas);
	private final CadastroEstacoes cadastroEstacoes = new CadastroEstacoes(repoEstacoes);
	private final CadastroAlugueis cadastroAlugueis = new CadastroAlugueis(repoUsuarios, repoBicicletas, repoAlugueis);
	private final ConsultaUsuarios consultaUsuarios = new ConsultaUsuarios(repoUsuarios);
	private final ConsultaEstacoes consultaEstacoes  = new ConsultaEstacoes(repoEstacoes);
	
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
		repo.removerUsuario(usuario);
	}
	
	public String criarEstacao(String nome, String endereco, String capacidade) throws Exception {
		return cadastroEstacoes.criarEstacao(nome, endereco, capacidade);
	}
	
	public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) throws Exception {
		cadastroEstacoes.criarBicicleta(estacao, bicicleta, tipo, nivelBateria);
	}
	
	public void removerBicicleta(String estacao, String bicicleta) throws Exception{
		repo.removerBicicleta(estacao, bicicleta);
	}
	
	public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data) throws Exception{
		return repo.retirarBicicleta(usuario, estacao, bicicleta, data);
	}
	
	public void zerarSistema() {
		repo.zerarSistema();
	}
	
	
	
	/////////////
	//Não alteram o sistema
	public String getAtributoUsuario(String Usuario, String atributo) throws Exception{
		return consultaUsuarios.getAtributoUsuario(Usuario, atributo);
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
		return consultaEstacoes.getAtributoBicicleta(bicicleta, atributo);
	}
	
	public String getEstadoBicicleta(String bicicleta) throws Exception {
		return consultaEstacoes.getAtributoBicicleta(bicicleta, "estado");
	}
	
	public String getAtributoAluguel(String aluguel, String atributo) throws Exception {
		return consultaEstacoes.getAtributoAluguel(aluguel, atributo);
	}
	
	public void encerrarSistema() {
		repo.salvar();
	}
}
