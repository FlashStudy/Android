package com.android.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.android.crud.model.Disciplina;

//Repository das disciplinas (Comunicação com o BD)
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {

	// Lista as disciplinas do usuário
		@Query(value = "SELECT * FROM Disciplina WHERE codigo_usuario = ?1", nativeQuery = true)
		List<Disciplina> getByUsuarioCodigo(Long codigo);
}