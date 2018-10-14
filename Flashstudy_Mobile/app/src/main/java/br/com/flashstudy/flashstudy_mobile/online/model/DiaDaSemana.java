package br.com.flashstudy.flashstudy_mobile.online.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "codigo", "nome", "horarios", "usuario"})
public class DiaDaSemana implements Serializable {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("horarios")
    private Set<Horario> horarios = new HashSet<>();

    @JsonProperty("usuario")
    private Usuario usuario;

    public DiaDaSemana() {
        super();
    }

    public DiaDaSemana(Long codigo, String nome, Set<Horario> horarios) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.horarios = horarios;
    }

    @JsonProperty("codigo")
    public Long getCodigo() {
        return codigo;
    }

    @JsonProperty("codigo")
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @JsonProperty("nome")
    public String getNome() {
        return nome;
    }

    @JsonProperty("nome")
    public void setNome(String nome) {
        this.nome = nome;
    }

    @JsonProperty("horarios")
    public Set<Horario> getHorarios() {
        return horarios;
    }

    @JsonProperty("horarios")
    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    @JsonProperty("usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty("usuario")
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void addHorario(Horario horario) {
        horarios.add(horario);
        horario.setDia(this);
    }

    public void removeHorario(Horario horario) {
        horarios.remove(horario);
        horario.setDia(null);
    }

}
