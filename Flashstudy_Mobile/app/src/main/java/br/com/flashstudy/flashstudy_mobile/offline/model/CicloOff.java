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

@Entity(tableName = "ciclo",
        foreignKeys = {@ForeignKey( entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo")},
        indices = {@Index(value = {"codigo"}, unique = true),
                   @Index(value = "usuario_codigo")})
public class CicloOff implements Serializable {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long codigo;

    @Ignore
    private List<HorarioOff> horarios = new ArrayList<>();

    @NonNull
    @ColumnInfo(name = "numero_de_materias")
    private int numMaterias;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private long usuarioCodigo;

    public CicloOff() {
    }

    @Ignore
    public CicloOff(@NonNull int numMaterias, @NonNull long usuarioCodigo) {
        this.numMaterias = numMaterias;
        this.usuarioCodigo = usuarioCodigo;
    }

    @NonNull
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull long codigo) {
        this.codigo = codigo;
    }

    public List<HorarioOff> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioOff> horarios) {
        this.horarios = horarios;
    }

    @NonNull
    public int getNumMaterias() {
        return numMaterias;
    }

    public void setNumMaterias(@NonNull int numMaterias) {
        this.numMaterias = numMaterias;
    }

    @NonNull
    public long getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull long usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    public void addHorario(HorarioOff horarioOff){
        horarios.add(horarioOff);
    }

    @Override
    public String toString() {
        return "CicloOff{" +
                "codigo=" + codigo +
                ", horarios=" + horarios +
                ", numMaterias=" + numMaterias +
                ", usuarioCodigo=" + usuarioCodigo +
                '}';
    }
}