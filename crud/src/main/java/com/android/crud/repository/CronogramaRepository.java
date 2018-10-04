package com.android.crud.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.android.crud.model.Cronograma;

//Repository do cronograma (Comunicação com o BD)
public interface CronogramaRepository extends CrudRepository<Cronograma, Long> {

	// Procura o cronograma atual do usuário
	@Query(value = "SELECT * FROM Cronograma WHERE codigo_usuario = ?1", nativeQuery = true)
	Cronograma getByUsuarioCodigo(Long codigo);

}