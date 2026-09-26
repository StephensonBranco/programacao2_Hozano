package br.ufal.ic.p2.wepayu.models;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private double salario;
    private Boolean sindicalizado;

    public Empregado() {}
    
    public Empregado(String nome, String endereco, String tipo, double salario) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.sindicalizado = false;
    }

    public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public void setSindicalizado(Boolean sindicalizado) {
		this.sindicalizado = sindicalizado;
	}

	public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public double getSalario() {
        return salario;
    }

    public Boolean getSindicalizado() {
    	return sindicalizado;
    }
}
