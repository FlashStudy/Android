package br.com.flashstudy.flashstudy_mobile.online.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Flashcard implements Serializable {

    private Long codigo;

    private String pergunta;

    private String resposta;

    private String nivel;

    private String titulo;

    private boolean publico;

    @JsonBackReference
    private Usuario usuario;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Disciplina disciplina;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    @Override
    public String toString() {
        return "FlashcardOff{" + "codigo=" + codigo + ", pergunta=" + pergunta + ", resposta=" + resposta + ", nivel="
                + nivel + ", titulo=" + titulo + ", publico=" + publico + ", usuario=" + usuario + ", disciplina="
                + disciplina + ", assunto=" + assunto + '}';
    }

}