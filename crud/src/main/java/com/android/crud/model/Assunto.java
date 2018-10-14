package com.android.crud.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "Assunto")
@SuppressWarnings("serial")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"codigo", "tema", "disciplina", "usuario"})
public class Assunto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("codigo")
	private Long codigo;

	@Column(name = "tema", nullable = false, unique = true)
    @JsonProperty("tema")
	private String tema;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "disciplina_codigo")
	@JsonBackReference
    @JsonProperty("disciplina")
	private Disciplina disciplina;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_usuario")
    @JsonProperty("usuario")
	private Usuario usuario;

	public Assunto() {
        super();
    }

    public Assunto(Long codigo, String tema, Disciplina disciplina, Usuario usuario) {
        super();
        this.codigo = codigo;
        this.tema = tema;
        this.disciplina = disciplina;
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

    @JsonProperty("tema")
    public String getTema() {
        return tema;
    }

    @JsonProperty("tema")
    public void setTema(String tema) {
        this.tema = tema;
    }

    @JsonProperty("disciplina")
    public Disciplina getDisciplina() {
        return disciplina;
    }

    @JsonProperty("disciplina")
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @JsonProperty("usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty("usuario")
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Assunto{" +
                "codigo=" + codigo +
                ", tema='" + tema + '\'' +
                ", disciplina=" + disciplina +
                ", usuario=" + usuario +
                '}';
    }
}