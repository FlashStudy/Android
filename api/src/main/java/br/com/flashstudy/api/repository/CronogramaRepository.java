package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Cronograma;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//Repository do Cronograma (Comunicação com o BD)
public interface CronogramaRepository extends CrudRepository<Cronograma, Long> {

    // Procura o cronograma atual do usuário
    @Query(value = "SELECT * FROM Cronograma WHERE codigo_usuario = ?1", nativeQuery = true)
    Cronograma getByUsuarioCodigo(Long codigo);

}
