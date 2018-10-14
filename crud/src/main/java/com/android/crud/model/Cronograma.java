package com.android.crud.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "Cronograma")
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "codigo", "inicio", "fim", "usuario", "disciplinas" })
public class Cronograma implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo", unique = true, nullable = false)
	@JsonProperty("codigo")
	private Long codigo;

	@Column(name = "inicio", nullable = false)
	@JsonProperty("inicio")
	private String inicio;

	@Column(name = "fim", nullable = false)
	@JsonProperty("fim")
	private String fim;

	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	@JsonBackReference
	@JsonProperty("usuario")
	private Usuario usuario;

	@JsonProperty("disciplinas")
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "cronograma_possui_disciplinas", joinColumns = {
			@JoinColumn(name = "cronograma_codigo") }, inverseJoinColumns = { @JoinColumn(name = "disciplina_codigo") })
	private List<Disciplina> disciplinas;

	public Cronograma() {
    }

    public Cronograma(String inicio, String fim) {
        super();
        this.inicio = inicio;
        this.fim = fim;
    }

    public Cronograma(String inicio, String fim, Usuario usuario) {
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario;
    }

    public Cronograma(String inicio, String fim, Usuario usuario, List<Disciplina> disciplinas) {
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario;
        this.disciplinas = disciplinas;
    }

    public Cronograma(Long codigo, String inicio, String fim, Usuario usuario) {
        this.codigo = codigo;
        this.inicio = inicio;
        this.fim = fim;
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

    @JsonProperty("inicio")
    public String getInicio() {
        return inicio;
    }

    @JsonProperty("inicio")
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    @JsonProperty("fim")
    public String getFim() {
        return fim;
    }

    @JsonProperty("fim")
    public void setFim(String fim) {
        this.fim = fim;
    }

    @JsonProperty("usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty("usuario")
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JsonProperty("disciplinas")
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    @JsonProperty("disciplinas")
    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
    
    public void addDisciplina(Disciplina disciplina) {
    	disciplinas.add(disciplina);
    }

    @Override
    public String toString() {
        return "Cronograma{" + "codigo=" + codigo + ", inicio=" + inicio + ", fim=" + fim + ", usuario=" + usuario
                + ", disciplinas=" + disciplinas + '}';
    }

}