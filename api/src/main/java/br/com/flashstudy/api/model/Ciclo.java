package br.com.flashstudy.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Ciclo")
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Ciclo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<DiaDaSemana> dias = new HashSet<>();

	@Column(name = "numero_de_materias", nullable = false)
	private Integer numMaterias;

	@ManyToOne
        @JoinColumn(name = "codigo_usuario")
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

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Set<DiaDaSemana> getDias() {
		return dias;
	}

	public void setDias(Set<DiaDaSemana> dias) {
		this.dias = dias;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getNumMaterias() {
		return numMaterias;
	}

	public void setNumMaterias(Integer numMaterias) {
		this.numMaterias = numMaterias;
	}

	public void addDiaDaSemana(DiaDaSemana diaDaSemana) {
		dias.add(diaDaSemana);
	}

	public void removeDiaDaSemana(DiaDaSemana diaDaSemana) {
		dias.remove(diaDaSemana);
	}

	@Override
	public String toString() {
		return "Ciclo [codigo=" + codigo + ", dias=" + dias + ", numMaterias=" + numMaterias + ", usuario=" + usuario
				+ "]";
	}

}