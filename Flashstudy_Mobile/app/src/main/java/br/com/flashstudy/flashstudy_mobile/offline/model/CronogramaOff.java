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

@Entity(tableName = "cronograma",
        foreignKeys = {@ForeignKey( entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo")},
        indices = { @Index(value = {"codigo"}, unique = true),
                    @Index(value = {"usuario_codigo"})})
public class CronogramaOff {

    @NonNull
    @PrimaryKey
    private int codigo;

    @NonNull
    private String inicio;

    @NonNull
    private String fim;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private int usuarioCodigo;

    @Ignore
    private List<DisciplinaOff> disciplinas = new ArrayList<>();

    public CronogramaOff() {
    }

    @Ignore
    public CronogramaOff(@NonNull String inicio, @NonNull String fim, @NonNull int usuarioCodigo) {
        this.inicio = inicio;
        this.fim = fim;
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
    public String getInicio() {
        return inicio;
    }

    public void setInicio(@NonNull String inicio) {
        this.inicio = inicio;
    }

    @NonNull
    public String getFim() {
        return fim;
    }

    public void setFim(@NonNull String fim) {
        this.fim = fim;
    }

    @NonNull
    public int getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull int usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    public List<DisciplinaOff> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<DisciplinaOff> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void addDisciplina(DisciplinaOff disciplina) {
        disciplinas.add(disciplina);
    }

    @Override
    public String toString() {
        return "CronogramaOff{" +
                "codigo=" + codigo +
                ", inicio='" + inicio + '\'' +
                ", fim='" + fim + '\'' +
                ", usuarioCodigo=" + usuarioCodigo +
                ", disciplinas=" + disciplinas +
                '}';
    }
}