package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "horario",
        foreignKeys = { @ForeignKey(entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo"),
                        @ForeignKey(entity = DisciplinaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "disciplina_codigo"),
                        @ForeignKey(entity = DiaDaSemanaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "dia_codigo")},
        indices = { @Index(value = {"codigo"}, unique = true),
                    @Index(value = {"usuario_codigo"}),
                    @Index(value = {"disciplina_codigo"}),
                    @Index(value = {"dia_codigo"})})
public class HorarioOff {

    @NonNull
    @PrimaryKey
    private int codigo;

    @NonNull
    private int tempo;

    @NonNull
    @ColumnInfo(name = "disciplina_codigo")
    private int disciplinaCodigo;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private int usuarioCodigo;

    @NonNull
    @ColumnInfo(name = "dia_codigo")
    private int diaCodigo;

    public HorarioOff() {
    }

    @Ignore
    public HorarioOff(@NonNull int tempo, @NonNull int disciplinaCodigo, @NonNull int usuarioCodigo, @NonNull int diaCodigo) {
        this.tempo = tempo;
        this.disciplinaCodigo = disciplinaCodigo;
        this.usuarioCodigo = usuarioCodigo;
        this.diaCodigo = diaCodigo;
    }

    @NonNull
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull int codigo) {
        this.codigo = codigo;
    }

    @NonNull
    public int getTempo() {
        return tempo;
    }

    public void setTempo(@NonNull int tempo) {
        this.tempo = tempo;
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

    @NonNull
    public int getDiaCodigo() {
        return diaCodigo;
    }

    public void setDiaCodigo(@NonNull int diaCodigo) {
        this.diaCodigo = diaCodigo;
    }

    @Override
    public String toString() {
        return "HorarioOff{" +
                "codigo=" + codigo +
                ", tempo=" + tempo +
                ", disciplinaCodigo=" + disciplinaCodigo +
                ", usuarioCodigo=" + usuarioCodigo +
                ", diaCodigo=" + diaCodigo +
                '}';
    }
}