package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.DiaDaSemanaOff;

@Dao
public interface DiaDaSemanaDao {

    //Insere
    @Insert
    void insert(DiaDaSemanaOff diaDaSemana);

    //Insere uma lista
    @Insert
    void insertLista(List<DiaDaSemanaOff> diaDaSemana);

    //Deleta
    @Delete
    void delete(DiaDaSemanaOff diaDaSemana);

    //Atualiza
    @Update
    void update(DiaDaSemanaOff diaDaSemana);

    //Procura todos os Dias de um ciclo de um usuário
    @Query("SELECT * FROM dia_da_semana WHERE usuario_codigo = :codigoUsuario ORDER BY codigo ASC")
    List<DiaDaSemanaOff> getAllDiasFromUsuarioCodigo(long codigoUsuario);
}
