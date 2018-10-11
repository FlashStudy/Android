package br.com.flashstudy.flashstudy_mobile.online.model;


import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "codigo", "nome", "assuntos", "usuario"})
public class Disciplina implements Serializable {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("assuntos")
    private List<Assunto> assuntos;

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

    public Disciplina(Long codigo, String nome, List<Assunto> assuntos, Usuario usuario) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.assuntos = assuntos;
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

    @JsonProperty("assuntos")
    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    @JsonProperty("assuntos")
    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    @JsonProperty("usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty("usuario")
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void addAssunto(Assunto assunto) {
        assuntos.add(assunto);
        assunto.setDisciplina(this);
    }

    public void removeAssunto(Assunto assunto) {
        assuntos.remove(assunto);
        assunto.setDisciplina(null);
    }

    @Override
    public String toString() {
        return "Disciplina [codigo=" + codigo + ", nome=" + nome + ", assuntos=" + assuntos + ", usuario=" + usuario
                + "]";
    }
}
