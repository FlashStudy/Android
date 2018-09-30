package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "assunto",
        foreignKeys = {@ForeignKey( entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo"),
                        @ForeignKey(entity = DisciplinaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "disciplina_codigo")},
        indices = { @Index(value = {"codigo"}, unique = true),
                    @Index(value = {"disciplina_codigo"}),
                    @Index(value = {"usuario_codigo"})})
public class AssuntoOff {

    @NonNull
    @PrimaryKey
    private int codigo;

    @NonNull
    private String tema;

    @NonNull
    @ColumnInfo(name = "disciplina_codigo")
    private int disciplinaCodigo;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private int usuarioCodigo;


    public AssuntoOff() {
    }

    @Ignore
    public AssuntoOff(@NonNull String tema, @NonNull int disciplinaCodigo, @NonNull int usuarioCodigo) {
        this.tema = tema;
        this.disciplinaCodigo = disciplinaCodigo;
        this.usuarioCodigo = usuarioCodigo;
    }

    @NonNull
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull int codigo) {
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
    public int getDisciplinaCodigo() {
        return disciplinaCodigo;
    }

    public void setDisciplinaCodigo(@NonNull int disciplinaCodigo) {
        this.disciplinaCodigo = disciplinaCodigo;
    }

    @NonNull
    public int getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull int usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    @Override
    public String toString() {
        return "AssuntoOff{" +
                "codigo=" + codigo +
                ", tema='" + tema + '\'' +
                ", disciplinaCodigo=" + disciplinaCodigo +
                ", usuarioCodigo=" + usuarioCodigo +
                '}';
    }
}