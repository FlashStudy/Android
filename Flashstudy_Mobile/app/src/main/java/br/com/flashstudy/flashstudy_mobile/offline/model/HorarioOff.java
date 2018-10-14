package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "horario",
        foreignKeys = { @ForeignKey(entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo"),
                        @ForeignKey(entity = DisciplinaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "disciplina_codigo",
                                    onDelete = ForeignKey.CASCADE,
                                    onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = DiaDaSemanaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "dia_codigo",
                                    onDelete = ForeignKey.CASCADE,
                                    onUpdate = ForeignKey.CASCADE)},
        indices = { @Index(value = {"codigo"}, unique = true),
                    @Index(value = {"usuario_codigo"}),
                    @Index(value = {"disciplina_codigo"}),
                    @Index(value = {"dia_codigo"})})
public class HorarioOff implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long codigo;

    @NonNull
    private long tempo;

    @NonNull
    @ColumnInfo(name = "disciplina_codigo")
    private long disciplinaCodigo;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private long usuarioCodigo;

    @NonNull
    @ColumnInfo(name = "dia_codigo")
    private long diaCodigo;

    public HorarioOff() {
    }

    @Ignore
    public HorarioOff(@NonNull long tempo, @NonNull long disciplinaCodigo, @NonNull long usuarioCodigo, @NonNull long diaCodigo) {
        this.tempo = tempo;
        this.disciplinaCodigo = disciplinaCodigo;
        this.usuarioCodigo = usuarioCodigo;
        this.diaCodigo = diaCodigo;
    }

    @NonNull
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull long codigo) {
        this.codigo = codigo;
    }

    @NonNull
    public long getTempo() {
        return tempo;
    }

    public void setTempo(@NonNull long tempo) {
        this.tempo = tempo;
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

    @NonNull
    public long getDiaCodigo() {
        return diaCodigo;
    }

    public void setDiaCodigo(@NonNull long diaCodigo) {
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