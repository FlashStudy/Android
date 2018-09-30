package br.com.flashstudy.flashstudy_mobile.online.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

public class Horario implements Serializable {

	private Long codigo;

	private Integer tempo;

	private Disciplina disciplina;

	private Usuario usuario;

	@JsonBackReference
	private DiaDaSemana dia;

	public Horario() {
		super();
	}

	public Horario(Integer tempo, Disciplina disciplina, Usuario usuario) {
		super();
		this.tempo = tempo;
		this.disciplina = disciplina;
		this.usuario = usuario;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DiaDaSemana getDia() {
		return dia;
	}

	public void setDia(DiaDaSemana dia) {
		this.dia = dia;
	}

}