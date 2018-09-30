package br.com.flashstudy.flashstudy_mobile.online.model;

import java.io.Serializable;
import java.util.List;

public class Pasta implements Serializable {

    private Long codigo;

    private String nome;

    private Usuario usuario;

    private List<Flashcard> flashcards;

    public Pasta(String nome, Usuario usuario, List<Flashcard> flashcards) {
        super();
        this.nome = nome;
        this.usuario = usuario;
        this.flashcards = flashcards;
    }

    public Pasta(String nome, Usuario usuario) {
        super();
        this.nome = nome;
        this.usuario = usuario;
    }

    public Pasta() {
        super();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Flashcard> getFlashcard() {
        return flashcards;
    }

    public void setFlashcard(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

}