package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Flashcard;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;

//Repository dos flashcards (Comunicação com o BD)
public interface FlashcardRepository extends CrudRepository<Flashcard, Long> {

    // Lista os flashcards do usuário
    @Query(value = "SELECT * FROM Flashcard WHERE usuario_codigo = ?1", nativeQuery = true)
    List<Flashcard> getByUsuario(Long codigo);

}
