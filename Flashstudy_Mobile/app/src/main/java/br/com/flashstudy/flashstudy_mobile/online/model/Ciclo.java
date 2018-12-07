package br.com.flashstudy.flashstudy_mobile.online.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "codigo", "dias", "numMaterias", "usuario"})
public class Ciclo implements Serializable {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("dias")
    private Set<DiaDaSemana> dias = new HashSet<>();

    @JsonProperty("numMaterias")
    private Integer numMaterias;

    @JsonProperty("usuario")
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

    @JsonProperty("codigo")
    public Long getCodigo() {
        return codigo;
    }

    @JsonProperty("codigo")
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @JsonProperty("dias")
    public Set<DiaDaSemana> getDias() {
        return dias;
    }

    @JsonProperty("dias")
    public void setDias(Set<DiaDaSemana> dias) {
        this.dias = dias;
    }

    @JsonProperty("usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty("usuario")
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JsonProperty("numMaterias")
    public Integer getNumMaterias() {
        return numMaterias;
    }

    @JsonProperty("numMaterias")
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