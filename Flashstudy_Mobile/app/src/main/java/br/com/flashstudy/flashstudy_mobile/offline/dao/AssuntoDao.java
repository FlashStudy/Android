package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.persistence.room.*;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;

@Dao
public interface AssuntoDao {

    //Insere
    @Insert
    void salvar(AssuntoOff assuntoOff);

    //Insere uma lista
    @Insert
    void salvarLista(List<AssuntoOff> assuntoOffs);

    //Deleta
    @Delete
    void deletar(AssuntoOff assuntoOff);

    //Atualiza
    @Update
    void atualizar(AssuntoOff assuntoOff);

    //Atualiza
    @Update
    void atualizarLista(List<AssuntoOff> assuntoOffs);

    //Procura Assuntos de uma Disciplina de um Usuario
    @Query("SELECT * FROM assunto WHERE disciplina_codigo = :codigoDisciplina ORDER BY codigo ASC")
    List<AssuntoOff> getAllAssuntosByDisciplina(long codigoDisciplina);

}
