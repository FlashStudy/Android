package com.android.crud.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Flashcard")
@SuppressWarnings("serial")
// @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Flashcard implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@Column(name = "pergunta", nullable = false)
	private String pergunta;

	@Column(name = "resposta", nullable = false)
	private String resposta;

	/*
	 * @Column(name = "nivel", nullable = false) private String nivel;
	 */

	@Column(name = "titulo", nullable = false)
	private String titulo;

	/*
	 * @Column(name = "publico", nullable = false) private boolean publico;
	 */

	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	@JsonBackReference
	private Usuario usuario;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "disciplina_codigo")
	 * 
	 * @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) private
	 * Disciplina disciplina;
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "assunto_codigo")
	 * 
	 * @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) private
	 * Assunto assunto;
	 */

	public Flashcard() {
		super();
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Flashcard [codigo=" + codigo + ", pergunta=" + pergunta + ", resposta=" + resposta + ", titulo="
				+ titulo + ", usuario=" + usuario + "]";
	}

}