package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.persistence.room.*;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;

@Dao
public interface AssuntoDao {

    //Insere
    @Insert
    void insert(AssuntoOff assunto);

    //Insere uma lista
    @Insert
    void insertLista(List<AssuntoOff> assuntos);

    //Deleta
    @Delete
    void delete(AssuntoOff assunto);

    //Atualiza
    @Update
    void update(AssuntoOff assunto);

    //Atualiza
    @Update
    void updateLista(List<AssuntoOff> assunto);

    //Procura Assuntos de uma Disciplina de um Usuario
    @Query("SELECT * FROM assunto WHERE disciplina_codigo = :codigoDisciplina ORDER BY codigo ASC")
    List<AssuntoOff> getAllAssuntosByDisciplina(long codigoDisciplina);

}
