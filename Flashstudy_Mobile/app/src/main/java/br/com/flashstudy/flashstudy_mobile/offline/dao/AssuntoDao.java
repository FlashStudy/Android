package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;

@Dao
public interface AssuntoDao {

    //Insere
    @Insert
    void insert(AssuntoOff assunto);

    //Deleta
    @Delete
    void delete(AssuntoOff assunto);

    //Atualiza
    @Update
    void update(AssuntoOff assunto);

    //Procura Assuntos de uma Disciplina de um Usuario
    @Query("SELECT * FROM assunto WHERE usuario_codigo = :codigoUsuario AND disciplina_codigo = :codigoDisciplina ORDER BY codigo ASC")
    LiveData<List<AssuntoOff>> getAllAssuntosByUsuarioAndDisciplina(int codigoUsuario, int codigoDisciplina);

}
