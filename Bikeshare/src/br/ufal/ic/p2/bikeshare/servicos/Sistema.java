package br.ufal.ic.p2.bikeshare.servicos;

public class Sistema {
	private final Repositorio repo = new Repositorio();
	private final CadastroUsuario cadastroUsuario = new CadastroUsuario(repo);
	private final Consulta consulta = new Consulta(repo);
	
	/////////////
	///Alteram o sistema
	public String criarUsuario(String nome, String email, String senha, String tipo, String cadastro,
            String razaoSocial, String plano, String mensalidade) throws Exception{
		String id = cadastroUsuario.criarUsuario(nome, email, senha, tipo, cadastro, razaoSocial, plano, mensalidade);
		return id;
	}
	
	public void alterarUsuario(String usuario, String atributo, String valor) throws Exception {
		cadastroUsuario.alterarUsuario(usuario, atributo, valor);
	}
	
	public void zerarSistema() {
		repo.zerarSistema();
	}
	
	/////////////
	
	//Não alteram o sistema
	public String getAtributoUsuario(String Usuario, String atributo) throws Exception{
		return consulta.getAtributoUsuario(Usuario, atributo);
	}
	
	public String getUsuarioPorEmail(String email) throws Exception{
		return consulta.getUsuarioPorEmail(email);
	}
	
	public void encerrarSistema() {
		repo.salvar();
	}
}
