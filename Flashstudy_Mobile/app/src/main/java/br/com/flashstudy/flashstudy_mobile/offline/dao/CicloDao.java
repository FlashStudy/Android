package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;

@Dao
public interface CicloDao {

    //Insere
    @Insert
    void salvar(CicloOff cicloOff);

    //Deleta
    @Delete
    void deletar(CicloOff cicloOff);

    //Atualiza
    @Update
    void atualizar(CicloOff cicloOff);

    //Procura o Ciclo de um Usuario
    @Query("SELECT * FROM ciclo WHERE usuario_codigo = :codigo")
    CicloOff getCicloByUsuario(long codigo);
}
