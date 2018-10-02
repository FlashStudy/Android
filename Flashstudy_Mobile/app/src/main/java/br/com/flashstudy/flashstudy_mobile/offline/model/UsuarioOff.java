package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "usuario", indices = {@Index(value = {"codigo"}, unique = true), @Index(value = {"email"}, unique = true)})
public class UsuarioOff {

    @NonNull
    @PrimaryKey
    private long codigo;

    @NonNull
    private String nome;

    @NonNull
    private String email;

    @NonNull
    private String senha;

    public UsuarioOff() {
    }

    @Ignore
    public UsuarioOff(@NonNull long codigo, @NonNull String nome, @NonNull String email, @NonNull String senha) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Ignore
    public UsuarioOff(@NonNull String nome, @NonNull String email, @NonNull String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getSenha() {
        return senha;
    }

    public void setSenha(@NonNull String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "UsuarioOff{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
