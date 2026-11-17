package br.ufal.ic.p2.bikeshare.Sistema;

public class Sistema {
	private final Repositorio repo = new Repositorio();
	private final CadastroUsuario cadastroUsuario = new CadastroUsuario(repo);
	private final Consulta consulta = new Consulta(repo);
	
	/////////////
	///Alteram o sistema
	public String criarUsuario(String nome, String email, String senha, String tipo, String cadastro, String estado,
            String razaoSocial, String plano, String mensalidade, String metodoPagamento) throws Exception{
		String id = cadastroUsuario.criarUsuario(nome, email, senha, tipo, cadastro, estado, razaoSocial, plano, mensalidade, metodoPagamento);
		return id;
	}
	
	public void zerarSistema() {
		repo.zerarSistema();
	}
	/////////////
	
	//Não alteram o sistema
	public String getAtributoUsuario(String Usuario, String atributo) throws Exception{
		return consulta.getAtributoUsuario(Usuario, atributo);
	}
}
