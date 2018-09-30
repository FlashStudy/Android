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

@Entity(tableName = "ciclo",
        foreignKeys = {@ForeignKey( entity = UsuarioOff.class,
                                    parentColumns = "codigo",
                                    childColumns = "usuario_codigo")},
        indices = { @Index(value = {"codigo"}, unique = true),
                    @Index(value = "usuario_codigo")})
public class CicloOff {


    @NonNull
    @PrimaryKey
    private int codigo;

    @Ignore
    private List<DiaDaSemanaOff> dias = new ArrayList<>();

    @NonNull
    @ColumnInfo(name = "numero_de_materias")
    private int numMaterias;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private int usuarioCodigo;

    public CicloOff() {
    }

    @Ignore
    public CicloOff(List<DiaDaSemanaOff> dias, @NonNull int numMaterias, @NonNull int usuarioCodigo) {
        this.dias = dias;
        this.numMaterias = numMaterias;
        this.usuarioCodigo = usuarioCodigo;
    }

    @NonNull
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull int codigo) {
        this.codigo = codigo;
    }

    public List<DiaDaSemanaOff> getDias() {
        return dias;
    }

    public void setDias(List<DiaDaSemanaOff> dias) {
        this.dias = dias;
    }

    @NonNull
    public int getNumMaterias() {
        return numMaterias;
    }

    public void setNumMaterias(@NonNull int numMaterias) {
        this.numMaterias = numMaterias;
    }

    @NonNull
    public int getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull int usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    public void addDiaDaSemana(DiaDaSemanaOff diaDaSemana) {
        dias.add(diaDaSemana);
    }

    @Override
    public String toString() {
        return "CicloOff{" +
                "codigo=" + codigo +
                ", dias=" + dias +
                ", numMaterias=" + numMaterias +
                ", usuarioCodigo=" + usuarioCodigo +
                '}';
    }
}