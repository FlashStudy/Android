package br.com.flashstudy.api.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Disciplina")
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Disciplina implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo", unique = true, nullable = false)
	private Long codigo;

	@Column(name = "nome", nullable = false)
	private String nome;

	@OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
	private Set<Assunto> assuntos = new HashSet<>();

	@ManyToOne
        @JoinColumn(name = "codigo_usuario")
	private Usuario usuario;

	public Disciplina() {
		super();
	}

	public Disciplina(Long codigo, String nome, Usuario usuario) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.usuario = usuario;
	}

	public Disciplina(Long codigo, String nome, Set<Assunto> assuntos, Usuario usuario) {
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

	public Set<Assunto> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(Set<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void addAssunto(Assunto assunto) {
		assuntos.add(assunto);
		assunto.setDisciplina(this);
	}

	public void removeAssunto(Assunto assunto) {
		assuntos.remove(assunto);
		assunto.setDisciplina(null);
	}
}
