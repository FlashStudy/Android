package br.com.flashstudy.flashstudy_mobile.online.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"codigo", "tempo", "disciplina", "usuario", "diaDaSemana"})
public class Horario implements Serializable {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("tempo")
    private Integer tempo;

    @JsonProperty("disciplina")
    private Disciplina disciplina;

    @JsonProperty("usuario")
    private Usuario usuario;

    @JsonBackReference
    @JsonProperty("diaDaSemana")
    private DiaDaSemana dia;

    public Horario() {
        super();
    }

    public Horario(Integer tempo, Disciplina disciplina, Usuario usuario) {
        super();
        this.tempo = tempo;
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

    @JsonProperty("tempo")
    public Integer getTempo() {
        return tempo;
    }

    @JsonProperty("tempo")
    public void setTempo(Integer tempo) {
        this.tempo = tempo;
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

    @JsonProperty("diaDaSemana")
    public DiaDaSemana getDia() {
        return dia;
    }

    @JsonProperty("diaDaSemana")
    public void setDia(DiaDaSemana dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "codigo=" + codigo +
                ", tempo=" + tempo +
                ", disciplina=" + disciplina +
                ", usuario=" + usuario +
                ", dia=" + dia +
                '}';
    }
}