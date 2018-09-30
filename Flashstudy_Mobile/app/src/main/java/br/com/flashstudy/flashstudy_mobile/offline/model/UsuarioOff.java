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
    private int codigo;

    @NonNull
    private String nome;

    @NonNull
    private String email;

    @NonNull
    private String senha;

    public UsuarioOff() {
    }

    @Ignore
    public UsuarioOff(@NonNull String nome, @NonNull String email, @NonNull String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Ignore
    public UsuarioOff(int codigo, String nome, String email, String senha) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
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
