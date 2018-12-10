package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Assunto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//Repository dos assuntos (Comunicação com o BD)
public interface AssuntoRepository extends CrudRepository<Assunto, Long> {
// Lista os flashcards do usuário
    @Query(value = "SELECT * FROM Assunto WHERE usuario_codigo = ?1", nativeQuery = true)
    List<Assunto> getByUsuario(Long codigo);
    
    @Query(value = "SELECT * FROM Assunto WHERE disciplina_codigo = ?1", nativeQuery = true)
    List<Assunto> getByDisciplina(Long codigo);
    
    
    
}