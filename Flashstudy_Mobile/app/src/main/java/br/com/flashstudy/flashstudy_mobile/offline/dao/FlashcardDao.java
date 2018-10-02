package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;

@Dao
public interface FlashcardDao {

    //Insere
    @Insert
    void insert(FlashcardOff flashcard);

    //Deleta
    @Delete
    void delete(FlashcardOff flashcard);

    //Atualiza
    @Update
    void update(FlashcardOff flashcard);

    //Procura Flashcards de um Usuario
    @Query("SELECT * FROM flashcard WHERE usuario_codigo = :codigo ORDER BY codigo ASC")
    List<FlashcardOff> getAllFlashcardsByUsuario(long codigo);

    /*//Procura Flashcards de uma Disciplina de um Usuário
    @Query("SELECT * FROM flashcard WHERE disciplina_codigo = :codigoDisciplina AND usuario_codigo = :codigoUsuario ORDER BY codigo ASC")
    LiveData<List<FlashcardOff>> getAllFlashcardsByDisciplinaAndUsuario(int codigoDisciplina, int codigoUsuario);

    //Procura Flashcards de um Assunto de um Usuário
    @Query("SELECT * FROM flashcard WHERE assunto_codigo = :codigoAssunto AND usuario_codigo = :codigoUsuario ORDER BY codigo ASC")
    LiveData<List<FlashcardOff>> getAllFlashcardOffsByAssunto(int codigoAssunto, int codigoUsuario);

    //Procura Flashcards de uma Pasta do Usuario
    @Query("SELECT * FROM flashcard WHERE usuario_codigo = :codigoUsuario AND pasta_codigo = :codigoPasta ORDER BY codigo ASC")
    LiveData<List<FlashcardOff>> getAllFlashcardOffsByUsuarioAndPasta(int codigoUsuario, int codigoPasta);*/
}
