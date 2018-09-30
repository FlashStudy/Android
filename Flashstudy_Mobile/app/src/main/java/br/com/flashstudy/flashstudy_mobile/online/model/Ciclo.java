package br.com.flashstudy.flashstudy_mobile.online.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ciclo implements Serializable {

    private Long codigo;

    private Set<DiaDaSemana> dias = new HashSet<>();

    private Integer numMaterias;

    private Usuario usuario;

    public Ciclo() {
        super();
    }

    public Ciclo(Long codigo, Integer numMaterias, Usuario usuario) {
        super();
        this.codigo = codigo;
        this.numMaterias = numMaterias;
        this.usuario = usuario;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Set<DiaDaSemana> getDias() {
        return dias;
    }

    public void setDias(Set<DiaDaSemana> dias) {
        this.dias = dias;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getNumMaterias() {
        return numMaterias;
    }

    public void setNumMaterias(Integer numMaterias) {
        this.numMaterias = numMaterias;
    }

    public void addDiaDaSemana(DiaDaSemana diaDaSemana) {
        dias.add(diaDaSemana);
    }

    public void removeDiaDaSemana(DiaDaSemana diaDaSemana) {
        dias.remove(diaDaSemana);
    }

}