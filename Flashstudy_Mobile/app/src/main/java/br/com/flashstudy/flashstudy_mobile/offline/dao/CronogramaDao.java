package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;

@Dao
public interface CronogramaDao {

    //Insere
    @Insert
    void salvar(CronogramaOff cronogramaOff);

    //Deleta
    @Delete
    void deletar(CronogramaOff cronogramaOff);

    //Atualiza
    @Update
    void atualizar(CronogramaOff cronogramaOff);

    //Procura o Cronograma de um Usuario
    @Query("SELECT * FROM cronograma WHERE usuario_codigo = :codigo")
    CronogramaOff getCronogramaByUsuario(long codigo);
}
