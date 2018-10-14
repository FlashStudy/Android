package br.com.flashstudy.flashstudy_mobile.online.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"codigo", "tema", "disciplina", "usuario"})
public class Assunto implements Serializable {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("tema")
    private String tema;

    @JsonProperty("disciplina")
    private Disciplina disciplina;

    @JsonProperty("usuario")
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

    @JsonProperty("codigo")
    public Long getCodigo() {
        return codigo;
    }

    @JsonProperty("codigo")
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @JsonProperty("tema")
    public String getTema() {
        return tema;
    }

    @JsonProperty("tema")
    public void setTema(String tema) {
        this.tema = tema;
    }

    @JsonProperty("disciplina")
    public Disciplina getDisciplina() {
        return disciplina;
    }

    @JsonProperty("disciplina")
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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
        return "Assunto{" +
                "codigo=" + codigo +
                ", tema='" + tema + '\'' +
                ", disciplina=" + disciplina +
                ", usuario=" + usuario +
                '}';
    }
}
