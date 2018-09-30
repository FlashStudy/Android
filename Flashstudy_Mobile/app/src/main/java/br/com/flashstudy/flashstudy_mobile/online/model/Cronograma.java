package br.com.flashstudy.flashstudy_mobile.online.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cronograma implements Serializable {

    private Long codigo;

    private String inicio;

    private String fim;

    private Usuario usuario;

    private Set<Disciplina> disciplinas = new HashSet<>();

    public Cronograma() {
    }

    public Cronograma(String inicio, String fim) {
        super();
        this.inicio = inicio;
        this.fim = fim;
    }

    public Cronograma(String inicio, String fim, Usuario usuario) {
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario;
    }

    public Cronograma(String inicio, String fim, Usuario usuario, Set<Disciplina> disciplinas) {
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario;
        this.disciplinas = disciplinas;
    }

    public Cronograma(Long codigo, String inicio, String fim, Usuario usuario) {
        this.codigo = codigo;
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void addDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

}