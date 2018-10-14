package br.com.flashstudy.flashstudy_mobile.online.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"codigo", "nome", "email", "senha"})
public class Usuario implements Serializable {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("email")
    private String email;

    @JsonProperty("senha")
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

    public Usuario(String json) throws IOException {
        Usuario u = new ObjectMapper().readValue(json, Usuario.class);

        this.codigo = u.codigo;
        this.nome = u.nome;
        this.email = u.email;
        this.senha = u.senha;
    }

    @JsonProperty("codigo")
    public Long getCodigo() {
        return codigo;
    }

    @JsonProperty("codigo")
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @JsonProperty("nome")
    public String getNome() {
        return nome;
    }

    @JsonProperty("nome")
    public void setNome(String nome) {
        this.nome = nome;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("senha")
    public String getSenha() {
        return senha;
    }

    @JsonProperty("senha")
    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
