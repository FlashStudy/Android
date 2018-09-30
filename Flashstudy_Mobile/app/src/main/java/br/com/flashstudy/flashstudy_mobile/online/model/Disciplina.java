package br.com.flashstudy.flashstudy_mobile.online.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Disciplina implements Serializable {

    private Long codigo;

    private String nome;

    private Set<Assunto> assuntos = new HashSet<>();

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

    public Disciplina(Long codigo, String nome, Set<Assunto> assuntos, Usuario usuario) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.assuntos = assuntos;
        this.usuario = usuario;
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

    public Set<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(Set<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

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
}
