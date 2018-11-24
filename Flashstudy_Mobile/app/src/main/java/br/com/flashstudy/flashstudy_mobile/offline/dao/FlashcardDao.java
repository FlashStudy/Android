package br.com.flashstudy.flashstudy_mobile.offline.dao;

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
    void salvar(FlashcardOff flashcardOff);

    //Deleta
    @Delete
    void deletar(FlashcardOff flashcardOff);

    //Atualiza
    @Update
    void atualizar(FlashcardOff flashcardOff);

    //Procura Flashcards de um Usuario que não estão em uma pasta
    @Query("SELECT * FROM flashcard WHERE usuario_codigo = :codigo AND pasta_codigo IN (0) ORDER BY titulo ASC")
    List<FlashcardOff> getAllFlashcardsByUsuario(long codigo);

    //Procura Flashcards de uma Pasta do Usuario
    @Query("SELECT * FROM flashcard WHERE pasta_codigo = :codigoPasta ORDER BY codigo ASC")
    List<FlashcardOff> getAllFlashcardOffsByPasta(long codigoPasta);

    /*//Procura Flashcards de uma Disciplina de um Usuário
    @Query("SELECT * FROM flashcard WHERE disciplina_codigo = :codigoDisciplina AND usuario_codigo = :codigoUsuario ORDER BY codigo ASC")
    LiveData<List<FlashcardOff>> getAllFlashcardsByDisciplinaAndUsuario(int codigoDisciplina, int codigoUsuario);

    //Procura Flashcards de um Assunto de um Usuário
    @Query("SELECT * FROM flashcard WHERE assunto_codigo = :codigoAssunto AND usuario_codigo = :codigoUsuario ORDER BY codigo ASC")
    LiveData<List<FlashcardOff>> getAllFlashcardOffsByAssunto(int codigoAssunto, int codigoUsuario);
*/
}
