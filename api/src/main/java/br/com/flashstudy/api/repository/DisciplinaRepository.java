package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Disciplina;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//Repository das Disciplinas (Comunicação com o BD)
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {

    // Lista as disciplinas do usuário
    @Query(value = "SELECT * FROM Disciplina WHERE codigo_usuario = ?1", nativeQuery = true)
    List<Disciplina> getByUsuarioCodigo(Long codigo);
}
