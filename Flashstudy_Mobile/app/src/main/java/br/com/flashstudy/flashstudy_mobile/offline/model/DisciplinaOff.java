package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "disciplina",
        foreignKeys = {@ForeignKey(entity = UsuarioOff.class,
                parentColumns = "codigo",
                childColumns = "usuario_codigo")},
        indices = {@Index(value = {"codigo"}, unique = true),
                @Index(value = {"usuario_codigo"})})
public class DisciplinaOff implements Serializable {

    @NonNull
    @PrimaryKey
    private long codigo;

    @NonNull
    private String nome;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private long usuarioCodigo;

    @Ignore
    private List<AssuntoOff> assuntos = new ArrayList<>();

    public DisciplinaOff() {
    }

    @Ignore
    public DisciplinaOff(@NonNull String nome, @NonNull long usuarioCodigo, List<AssuntoOff> assuntos) {
        this.nome = nome;
        this.usuarioCodigo = usuarioCodigo;
        this.assuntos = assuntos;
    }

    @Ignore
    public DisciplinaOff(@NonNull long codigo, @NonNull String nome, @NonNull long usuarioCodigo) {
        this.codigo = codigo;
        this.nome = nome;
        this.usuarioCodigo = usuarioCodigo;
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

    public List<AssuntoOff> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<AssuntoOff> assuntos) {
        this.assuntos = assuntos;
    }

    @Override
    public String toString() {
        return nome;
    }

    public String toStringCompleto() {
        return "Disciplina {codigo=" + codigo + ", nome=" + nome + ", usuarioCodigo=" + usuarioCodigo + ", assuntos=" + assuntos + "}";
    }
}