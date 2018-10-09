package br.com.flashstudy.flashstudy_mobile.online.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assunto implements Serializable {

    private Long codigo;

    private String tema;

    private Disciplina disciplina;

    private Usuario usuario;

    public Assunto() {
        super();
    }

    public Assunto(Long codigo, String tema, Disciplina disciplina, Usuario usuario) {
        super();
        this.codigo = codigo;
        this.tema = tema;
        this.disciplina = disciplina;
        this.usuario = usuario;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Assunto{" +
                "codigo=" + codigo +
                ", tema='" + tema + '\'' +
                ", disciplina=" + disciplina +
                ", usuario=" + usuario +
                '}';
    }
}
