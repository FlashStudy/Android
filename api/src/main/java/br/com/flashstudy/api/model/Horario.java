package br.com.flashstudy.api.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Horario")
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.JOINED)
public class Horario implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo", unique = true, nullable = false)
	private Long codigo;

	@Column(name = "horario", nullable = false)
	private Integer tempo;

	@OneToOne
	private Disciplina disciplina;

	@ManyToOne
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dia_codigo")
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

	@Override
	public String toString() {
		return "Horario [codigo=" + codigo + ", tempo=" + tempo + ", disciplina=" + disciplina + ", usuario=" + usuario
				+ ", dia=" + dia + "]";
	}

}