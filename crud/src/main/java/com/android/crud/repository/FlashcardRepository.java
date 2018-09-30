package com.android.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.android.crud.model.Flashcard;


//Repository dos flashcards (Comunicação com o BD)
public interface FlashcardRepository extends CrudRepository<Flashcard, Long> {

	// Lista os flashcards do usuário
	@Query(value = "SELECT * FROM Flashcard WHERE codigo_usuario = ?1", nativeQuery = true)
	List<Flashcard> getByUsuarioCodigo(Long codigo);

}