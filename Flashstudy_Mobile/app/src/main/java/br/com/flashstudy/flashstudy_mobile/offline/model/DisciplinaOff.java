package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "disciplina",
        foreignKeys = { @ForeignKey(entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo"),
                        @ForeignKey(entity = CronogramaOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "cronograma_codigo")},
        indices = { @Index(value = {"codigo"}, unique = true),
                    @Index(value = {"usuario_codigo"}),
                    @Index(value = {"cronograma_codigo"})})
public class DisciplinaOff {

    @NonNull
    @PrimaryKey
    private long codigo;

    @NonNull
    private String nome;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private long usuarioCodigo;

    @NonNull
    @ColumnInfo(name = "cronograma_codigo")
    private long cronogramaCodigo;

    @Ignore
    private List<AssuntoOff> assuntos = new ArrayList<>();

    public DisciplinaOff() {
    }

    @Ignore
    public DisciplinaOff(@NonNull String nome, @NonNull long usuarioCodigo, @NonNull long cronogramaCodigo, List<AssuntoOff> assuntos) {
        this.nome = nome;
        this.usuarioCodigo = usuarioCodigo;
        this.cronogramaCodigo = cronogramaCodigo;
        this.assuntos = assuntos;
    }

    @NonNull
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull long codigo) {
        this.codigo = codigo;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @NonNull
    public long getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull long usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    @NonNull
    public long getCronogramaCodigo() {
        return cronogramaCodigo;
    }

    public void setCronogramaCodigo(@NonNull long cronogramaCodigo) {
        this.cronogramaCodigo = cronogramaCodigo;
    }

    public List<AssuntoOff> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<AssuntoOff> assuntos) {
        this.assuntos = assuntos;
    }

    @Override
    public String toString() {
        return "DisciplinaOff{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", usuarioCodigo=" + usuarioCodigo +
                ", cronogramaCodigo=" + cronogramaCodigo +
                ", assuntos=" + assuntos +
                '}';
    }
}