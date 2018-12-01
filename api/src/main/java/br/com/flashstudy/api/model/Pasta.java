package br.com.flashstudy.api.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Pasta")
@SuppressWarnings("serial")
public class Pasta implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@Column(name = "nome", nullable = false)
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;

	@OneToMany
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

	@Override
	public String toString() {
		return "Pasta [codigo=" + codigo + ", nome=" + nome + ", usuario=" + usuario + ", flashcards=" + flashcards
				+ "]";
	}

}