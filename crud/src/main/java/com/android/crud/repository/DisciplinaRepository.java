package com.android.crud.repository;

import java.util.List;

//Repository das disciplinas (Comunicação com o BD)
import org.springframework.data.repository.CrudRepository;

import com.android.crud.model.Disciplina;
import com.android.crud.model.Usuario;


//Repository das disciplinas (Comunicação com o BD)
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {

	// Lista as dsiciplinas do usuário
	List<Disciplina> getByUsuario(Usuario usuario);
}