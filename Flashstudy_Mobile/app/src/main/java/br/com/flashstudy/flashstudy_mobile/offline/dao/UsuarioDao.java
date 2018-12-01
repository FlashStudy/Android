package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;


@Dao
public interface UsuarioDao {

    @Insert
    void salvar(UsuarioOff usuarioOff);

    @Update
    void atualizar(UsuarioOff usuarioOff);

    //Procura Usuario por Email
    @Query("SELECT * FROM usuario WHERE email = :email")
    UsuarioOff getUsuarioByEmail(String email);

    @Query("SELECT * FROM usuario WHERE codigo = :codigo")
    UsuarioOff getUsuarioById(long codigo);
}
