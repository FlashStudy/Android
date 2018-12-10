package br.com.flashstudy.api.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Cronograma")
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Cronograma implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo", unique = true, nullable = false)
	private Long codigo;

	@Column(name = "inicio", nullable = false)
	private String inicio;

	@Column(name = "fim", nullable = false)
	private String fim;

	@ManyToOne
	private Usuario usuario;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "cronograma_possui_disciplinas", joinColumns = {
			@JoinColumn(name = "cronograma_codigo") }, inverseJoinColumns = { @JoinColumn(name = "disciplina_codigo") })
	private Set<Disciplina> disciplinas = new HashSet<>();

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

	public Cronograma(String inicio, String fim, Usuario usuario, Set<Disciplina> disciplinas) {
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

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
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