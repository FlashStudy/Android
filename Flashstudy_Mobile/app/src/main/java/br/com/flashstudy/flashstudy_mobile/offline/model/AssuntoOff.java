package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "assunto",
        foreignKeys = {@ForeignKey( entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo"),
                        @ForeignKey(entity = DisciplinaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "disciplina_codigo",
                                    onDelete = ForeignKey.CASCADE,
                                    onUpdate = ForeignKey.CASCADE)},
        indices = { @Index(value = {"codigo"}, unique = true),
                    @Index(value = {"disciplina_codigo"}),
                    @Index(value = {"usuario_codigo"})})
public class AssuntoOff implements Serializable {

    @NonNull
    @PrimaryKey
    private long codigo;

    @NonNull
    private String tema;

    @NonNull
    @ColumnInfo(name = "disciplina_codigo")
    private long disciplinaCodigo;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private long usuarioCodigo;


    public AssuntoOff() {
    }

    @Ignore
    public AssuntoOff(@NonNull String tema, @NonNull long disciplinaCodigo, @NonNull long usuarioCodigo) {
        this.tema = tema;
        this.disciplinaCodigo = disciplinaCodigo;
        this.usuarioCodigo = usuarioCodigo;
    }

    @Ignore
    public AssuntoOff(@NonNull long codigo, @NonNull String tema, @NonNull long disciplinaCodigo, @NonNull long usuarioCodigo) {
        this.codigo = codigo;
        this.tema = tema;
        this.disciplinaCodigo = disciplinaCodigo;
        this.usuarioCodigo = usuarioCodigo;
    }

    @NonNull
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull long codigo) {
        this.codigo = codigo;
    }

    @NonNull
    public String getTema() {
        return tema;
    }

    public void setTema(@NonNull String tema) {
        this.tema = tema;
    }

    @NonNull
    public long getDisciplinaCodigo() {
        return disciplinaCodigo;
    }

    public void setDisciplinaCodigo(@NonNull long disciplinaCodigo) {
        this.disciplinaCodigo = disciplinaCodigo;
    }

    @NonNull
    public long getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull long usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    @Override
    public String toString() {
        return tema;
    }

    public String toStringCompleto(){
        return "AssuntoOff{" +
                "codigo=" + codigo +
                ", tema='" + tema + '\'' +
                ", disciplinaCodigo=" + disciplinaCodigo +
                ", usuarioCodigo=" + usuarioCodigo +
                '}';
    }
}