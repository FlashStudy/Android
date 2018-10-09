package br.com.flashstudy.flashstudy_mobile.adapter.model;


import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.model.Assunto;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class DisciplinaListModel {

    private Long codigo;

    private String nome;

    private List<Assunto> assuntos = new ArrayList<>();

    private Usuario usuario;

    public DisciplinaListModel() {
        super();
    }

    public DisciplinaListModel(Long codigo, String nome, Usuario usuario) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.usuario = usuario;
    }

    public DisciplinaListModel(Long codigo, String nome, List<Assunto> assuntos, Usuario usuario) {
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

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return nome;
    }
}
