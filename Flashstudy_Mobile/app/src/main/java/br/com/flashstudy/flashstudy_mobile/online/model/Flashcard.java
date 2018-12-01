package br.com.flashstudy.flashstudy_mobile.online.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "codigo", "pergunta", "resposta", "nivel", "titulo", "publico", "usuario", "disicplina", "assunto"})
public class Flashcard implements Serializable {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("pergunta")
    private String pergunta;

    @JsonProperty("resposta")
    private String resposta;

    @JsonProperty("nivel")
    private String nivel;

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("publico")
    private boolean publico;

    @JsonProperty("usuario")
    private Usuario usuario;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonProperty("disciplina")
    private Disciplina disciplina;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonProperty("assunto")
    private Assunto assunto;

    public Flashcard() {
        super();
    }

    public Flashcard(Long codigo, String pergunta, String resposta, String nivel, String titulo, boolean publico,
                     Usuario usuario, Disciplina disciplina, Assunto assunto) {
        super();
        this.codigo = codigo;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.nivel = nivel;
        this.titulo = titulo;
        this.publico = publico;
        this.usuario = usuario;
        this.disciplina = disciplina;
        this.assunto = assunto;
    }

    @JsonProperty("codigo")
    public Long getCodigo() {
        return codigo;
    }

    @JsonProperty("codigo")
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @JsonProperty("pergunta")
    public String getPergunta() {
        return pergunta;
    }

    @JsonProperty("pergunta")
    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    @JsonProperty("resposta")
    public String getResposta() {
        return resposta;
    }

    @JsonProperty("resposta")
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    @JsonProperty("nivel")
    public String getNivel() {
        return nivel;
    }

    @JsonProperty("nivel")
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @JsonProperty("titulo")
    public String getTitulo() {
        return titulo;
    }

    @JsonProperty("titulo")
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @JsonProperty("publico")
    public boolean isPublico() {
        return publico;
    }

    @JsonProperty("publico")
    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    @JsonProperty("usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty("usuario")
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JsonProperty("disciplina")
    public Disciplina getDisciplina() {
        return disciplina;
    }

    @JsonProperty("disciplina")
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @JsonProperty("assunto")
    public Assunto getAssunto() {
        return assunto;
    }

    @JsonProperty("assunto")
    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    @Override
    public String toString() {
        return "Flashcard{" + "codigo=" + codigo + ", pergunta=" + pergunta + ", resposta=" + resposta + ", nivel="
                + nivel + ", titulo=" + titulo + ", publico=" + publico + ", usuario=" + usuario + ", disciplina="
                + disciplina + ", assunto=" + assunto + '}';
    }

}