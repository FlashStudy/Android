package com.android.crud.model;

import java.io.IOException;

import javax.persistence.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Table
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "senha", nullable = false)
	private String senha;

	public Usuario() {
        super();
    }
    
    public Usuario(String email, String senha) {
        super();
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha) {
        super();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(Long codigo, String nome, String email, String senha) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    
    public Usuario(String json) throws IOException{
    	Usuario u = new ObjectMapper().readValue(json, Usuario.class);
    	
    	 this.codigo = u.codigo;
         this.nome = u.nome;
         this.email = u.email;
         this.senha = u.senha;
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
