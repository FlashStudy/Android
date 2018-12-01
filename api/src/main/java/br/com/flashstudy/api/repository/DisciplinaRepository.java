package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Disciplina;
import br.com.flashstudy.api.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

//Repository das disciplinas (Comunicação com o BD)
import org.springframework.data.repository.CrudRepository;

//Repository das disciplinas (Comunicação com o BD)
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {

	// Lista as dsiciplinas do usuário
        @Query(value = "SELECT * FROM Disciplina WHERE codigo_usuario = ?1", nativeQuery = true)
	List<Disciplina> getByUsuario(Long codigo);
}