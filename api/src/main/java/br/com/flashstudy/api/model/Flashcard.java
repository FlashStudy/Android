package br.com.flashstudy.api.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity(name = "Flashcard")
@Table(name = "Flashcard")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"codigo", "pergunta", "resposta", "nivel", "titulo", "publico", "usuario", "disicplina", "assunto"})
public class Flashcard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("codigo")
    private Long codigo;

    @Column(name = "pergunta", nullable = false)
    @JsonProperty("pergunta")
    private String pergunta;

    @Column(name = "resposta", nullable = false)
    @JsonProperty("resposta")
    private String resposta;

    /*
	 * @Column(name = "nivel", nullable = false) private String nivel;
     */
    @Column(name = "titulo", nullable = false)
    @JsonProperty("titulo")
    private String titulo;

    /*
	 * @Column(name = "publico", nullable = false) private boolean publico;
     */
    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    @JsonProperty("usuario")
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

    public Flashcard(Long codigo, String pergunta, String resposta, String titulo,
            Usuario usuario) {
        super();
        this.codigo = codigo;
        this.pergunta = pergunta;
        this.resposta = resposta;
        //this.nivel = nivel;
        this.titulo = titulo;
        //this.publico = publico;
        this.usuario = usuario;
        //this.disciplina = disciplina;
        //this.assunto = assunto;
    }

    @JsonProperty("codigo")
    public Long getCodigo() {
        return codigo;
    }

    @JsonProperty("codigo")
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @JsonProperty("pergunta")
    public String getPergunta() {
        return pergunta;
    }

    @JsonProperty("pergunta")
    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    @JsonProperty("resposta")
    public String getResposta() {
        return resposta;
    }

    @JsonProperty("resposta")
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    /*
    @JsonProperty("nivel")
    public String getNivel() {
        return nivel;
    }

    @JsonProperty("nivel")
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
     */
    @JsonProperty("titulo")
    public String getTitulo() {
        return titulo;
    }

    @JsonProperty("titulo")
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /*
    @JsonProperty("publico")
    public boolean isPublico() {
        return publico;
    }

    @JsonProperty("publico")
    public void setPublico(boolean publico) {
        this.publico = publico;
    }
     */
    @JsonProperty("usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty("usuario")
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /*
    @JsonProperty("disciplina")
    public Disciplina getDisciplina() {
        return disciplina;
    }

    @JsonProperty("disciplina")
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @JsonProperty("assunto")
    public Assunto getAssunto() {
        return assunto;
    }

    @JsonProperty("assunto")
    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }
     */

    @Override
    public String toString() {
        return "Flashcard {codigo=" + codigo + ", pergunta=" + pergunta + ", resposta=" + resposta + ", titulo="
                + titulo + ", usuario=" + usuario + "}";
    }

}
