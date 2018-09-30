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

@Entity(tableName = "dia_da_semana",
        foreignKeys = { @ForeignKey(entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo"),
                        @ForeignKey(entity = CicloOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "ciclo_codigo")},
        indices = { @Index(value = {"codigo"}, unique = true),
                    @Index(value = {"usuario_codigo"}),
                    @Index(value = {"ciclo_codigo"})})
public class DiaDaSemanaOff {

    @NonNull
    @PrimaryKey
    private int codigo;

    @NonNull
    private String nome;

    @Ignore
    private List<HorarioOff> horarios = new ArrayList<>();

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private int usuarioCodigo;

    @NonNull
    @ColumnInfo(name = "ciclo_codigo")
    private int cicloCodigo;

    public DiaDaSemanaOff() {
    }

    @Ignore
    public DiaDaSemanaOff(@NonNull String nome, @NonNull int usuarioCodigo, @NonNull int cicloCodigo) {
        this.nome = nome;
        this.usuarioCodigo = usuarioCodigo;
        this.cicloCodigo = cicloCodigo;
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

    public List<HorarioOff> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioOff> horarios) {
        this.horarios = horarios;
    }

    @NonNull
    public int getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull int usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    @NonNull
    public int getCicloCodigo() {
        return cicloCodigo;
    }

    public void setCicloCodigo(@NonNull int cicloCodigo) {
        this.cicloCodigo = cicloCodigo;
    }

    public void addHorario(HorarioOff horario) {
        horarios.add(horario);
        horario.setDiaCodigo(codigo);
    }

    @Override
    public String toString() {
        return "DiaDaSemanaOff{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", horarios=" + horarios +
                ", usuarioCodigo=" + usuarioCodigo +
                ", cicloCodigo=" + cicloCodigo +
                '}';
    }
}