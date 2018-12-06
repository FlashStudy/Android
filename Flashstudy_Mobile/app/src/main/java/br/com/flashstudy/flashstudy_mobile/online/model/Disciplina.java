package br.com.flashstudy.flashstudy_mobile.online.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "codigo", "nome", "usuario"})
public class Disciplina implements Serializable {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("usuario")
    private Usuario usuario;

    public Disciplina() {
        super();
    }

    public Disciplina(Long codigo, String nome, Usuario usuario) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.usuario = usuario;
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

    @JsonProperty("usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty("usuario")
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Disciplina {codigo=" + codigo + ", nome=" + nome + ", usuario=" + usuario
                + "}";
    }
}
