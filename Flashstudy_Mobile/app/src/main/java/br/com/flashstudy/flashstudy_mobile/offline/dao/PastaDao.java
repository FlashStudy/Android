package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;

@Dao
public interface PastaDao {
    //Insere
    @Insert
    void salvar(PastaOff pastaOff);

    //Deleta
    @Delete
    void deletar(PastaOff pastaOff);

    //Atualiza
    @Update
    void atualizar(PastaOff pastaOff);

    //Procura Pastas de um Usuario
    @Query("SELECT * FROM pasta WHERE usuario_codigo = :codigo ORDER BY codigo ASC")
    List<PastaOff> getAllPastasByUsuario(long codigo);

}
