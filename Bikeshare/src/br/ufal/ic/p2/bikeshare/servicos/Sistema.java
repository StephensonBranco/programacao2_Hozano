package br.ufal.ic.p2.bikeshare.servicos;

public class Sistema {
	private final Repositorio repo = new Repositorio();
	private final CadastroUsuarios cadastroUsuario = new CadastroUsuarios(repo);
	private final ConsultaUsuarios consultaUsuarios = new ConsultaUsuarios(repo);
	private final CadastroEstacoes cadastroEstacao = new CadastroEstacoes(repo);
	private final ConsultaEstacoes consultaEstacoes  = new ConsultaEstacoes(repo);
	
	/////////////
	///Alteram o sistema
	public String criarUsuario(String nome, String email, String senha, String tipo, String cadastro,
            String razaoSocial, String plano, String mensalidade) throws Exception{
		return cadastroUsuario.criarUsuario(nome, email, senha, tipo, cadastro, razaoSocial, plano, mensalidade);
	}
	
	public void alterarUsuario(String usuario, String atributo, String valor) throws Exception {
		cadastroUsuario.alterarUsuario(usuario, atributo, valor);
	}
	
	public void alterarUsuario(String usuario, String atributo, String valor1, String valor2) throws Exception {
		cadastroUsuario.alterarUsuario(usuario, atributo, valor1, valor2);
	}
	
	public void alterarUsuario(String usuario, String atributo, String valor, String numeroCartao, String validadeCartao) throws Exception {
		cadastroUsuario.alterarUsuario(usuario, atributo, valor, numeroCartao, validadeCartao);
	}
	
	public void removerUsuario(String usuario) throws Exception {
		repo.removerUsuario(usuario);
	}
	
	public String criarEstacao(String nome, String endereco, String capacidade) throws Exception {
		return cadastroEstacao.criarEstacao(nome, endereco, capacidade);
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
	
	public void encerrarSistema() {
		repo.salvar();
	}
}
