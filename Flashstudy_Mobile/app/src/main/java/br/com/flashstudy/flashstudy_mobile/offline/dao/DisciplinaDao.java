package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;

@Dao
public interface DisciplinaDao {

    //Insere
    @Insert
    void insert(DisciplinaOff disciplina);

    //Insere uma lista
    @Insert
    void insert(List<DisciplinaOff> lis);

    //Deleta
    @Delete
    void delete(DisciplinaOff disciplina);

    //Atualiza
    @Update
    void update(DisciplinaOff disciplina);

    //Procura todas as disciplinas
    @Query("SELECT * FROM disciplina ORDER BY codigo ASC")
    List<DisciplinaOff> getAllDisciplinas();

    //Procura as disciplinas de um Cronograma
    @Query("SELECT * FROM disciplina WHERE usuario_codigo = :codigo ORDER BY codigo ASC")
    List<DisciplinaOff> getDisciplinaOffsByUsuario(long codigo);
}
