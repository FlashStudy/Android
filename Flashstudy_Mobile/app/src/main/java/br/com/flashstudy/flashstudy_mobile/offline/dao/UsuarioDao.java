package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;


@Dao
public interface UsuarioDao {

    @Insert
    void insert(UsuarioOff usuario);

    @Delete
    void delete(UsuarioOff usuario);

    @Update
    void update(UsuarioOff usuario);

    //Procura Usuario por Email
    @Query("SELECT * FROM usuario WHERE email = :email")
    UsuarioOff getUsuarioByEmail(String email);

    @Query("SELECT * FROM usuario")
    List<UsuarioOff> getUsuarios();

    @Query("SELECT * FROM usuario WHERE codigo = :codigo")
    UsuarioOff getUsuarioById(int codigo);
}
