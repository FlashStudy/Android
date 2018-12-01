package br.com.flashstudy.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Dia")
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class DiaDaSemana implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo", unique = true, nullable = false)
	private Long codigo;

	@Column(name = "nome_dia", unique = true, nullable = false)
	private String nome;

	@OneToMany(mappedBy = "dia", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Horario> horarios = new HashSet<>();

	@ManyToOne
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

	public Set<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(Set<Horario> horarios) {
		this.horarios = horarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

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
