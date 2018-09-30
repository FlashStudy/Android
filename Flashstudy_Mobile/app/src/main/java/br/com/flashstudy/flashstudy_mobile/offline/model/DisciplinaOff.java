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
    private int codigo;

    @NonNull
    private String nome;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private int usuarioCodigo;

    @NonNull
    @ColumnInfo(name = "cronograma_codigo")
    private int cronogramaCodigo;

    @Ignore
    private List<AssuntoOff> assuntos = new ArrayList<>();

    public DisciplinaOff() {
    }

    @Ignore
    public DisciplinaOff(@NonNull String nome, @NonNull int usuarioCodigo, @NonNull int cronogramaCodigo, List<AssuntoOff> assuntos) {
        this.nome = nome;
        this.usuarioCodigo = usuarioCodigo;
        this.cronogramaCodigo = cronogramaCodigo;
        this.assuntos = assuntos;
    }

    @NonNull
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull int codigo) {
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
    public int getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull int usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    @NonNull
    public int getCronogramaCodigo() {
        return cronogramaCodigo;
    }

    public void setCronogramaCodigo(@NonNull int cronogramaCodigo) {
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