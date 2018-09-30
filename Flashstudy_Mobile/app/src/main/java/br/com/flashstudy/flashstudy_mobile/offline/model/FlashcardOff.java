package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;


/*,
                        @ForeignKey(entity = DisciplinaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "disciplina_codigo"),
                        @ForeignKey(entity = AssuntoOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "assunto_codigo"),
                        @ForeignKey(entity = PastaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "pasta_codigo")}


                                    ,
                @Index(value = {"usuario_codigo"}),
                @Index(value = {"disciplina_codigo"}),
                @Index(value = {"assunto_codigo"}),
                @Index(value = {"pasta_codigo"})*/


@Entity(tableName = "flashcard",
        foreignKeys = {@ForeignKey(entity = UsuarioOff.class,
                parentColumns = "codigo",
                childColumns = "usuario_codigo")},
        indices = {@Index(value = {"codigo"}, unique = true)})
public class FlashcardOff implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int codigo;

    @NonNull
    private String pergunta;

    @NonNull
    private String resposta;

    /*@NonNull
    private String nivel;*/

    @NonNull
    private String titulo;

    /*@NonNull
    private boolean publico;*/

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private int usuarioCodigo;

    /*@NonNull
    @ColumnInfo(name = "disciplina_codigo")
    private int disciplinaCodigo;

    @NonNull
    @ColumnInfo(name = "assunto_codigo")
    private int assuntoCodigo;

    @ColumnInfo(name = "pasta_codigo")
    private int pastaCodigo;*/

    public FlashcardOff() {
    }

    @Ignore
    public FlashcardOff(@NonNull int codigo, @NonNull String pergunta, @NonNull String resposta, @NonNull String titulo) {
        this.codigo = codigo;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.titulo = titulo;
    }

    @NonNull
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull int codigo) {
        this.codigo = codigo;
    }

    @NonNull
    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(@NonNull String pergunta) {
        this.pergunta = pergunta;
    }

    @NonNull
    public String getResposta() {
        return resposta;
    }

    public void setResposta(@NonNull String resposta) {
        this.resposta = resposta;
    }

    /*@NonNull
    public String getNivel() {
        return nivel;
    }

    public void setNivel(@NonNull String nivel) {
        this.nivel = nivel;
    }*/

    @NonNull
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NonNull String titulo) {
        this.titulo = titulo;
    }

    /*@NonNull
    public boolean isPublico() {
        return publico;
    }

    public void setPublico(@NonNull boolean publico) {
        this.publico = publico;
    }*/

    @NonNull
    public int getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull int usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    /*@NonNull
    public int getDisciplinaCodigo() {
        return disciplinaCodigo;
    }

    public void setDisciplinaCodigo(@NonNull int disciplinaCodigo) {
        this.disciplinaCodigo = disciplinaCodigo;
    }

    @NonNull
    public int getAssuntoCodigo() {
        return assuntoCodigo;
    }

    public void setAssuntoCodigo(@NonNull int assuntoCodigo) {
        this.assuntoCodigo = assuntoCodigo;
    }

    public int getPastaCodigo() {
        return pastaCodigo;
    }

    public void setPastaCodigo(int pastaCodigo) {
        this.pastaCodigo = pastaCodigo;
    }

    @Override
    public String toString() {
        return "FlashcardOff{" +
                "codigo=" + codigo +
                ", pergunta='" + pergunta + '\'' +
                ", resposta='" + resposta + '\'' +
                ", nivel='" + nivel + '\'' +
                ", titulo='" + titulo + '\'' +
                ", publico=" + publico +
                ", usuarioCodigo=" + usuarioCodigo +
                ", disciplinaCodigo=" + disciplinaCodigo +
                ", assuntoCodigo=" + assuntoCodigo +
                ", pastaCodigo=" + pastaCodigo +
                '}';
    }*/
}