package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Ciclo;
import br.com.flashstudy.api.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//Repository do ciclo (Comunicação com o BD)
public interface CicloRepository extends CrudRepository<Ciclo, Long> {

	// Solicita o ciclo do usuário
        @Query(value = "SELECT * FROM Ciclo WHERE usuario_codigo = ?1", nativeQuery = true)
	Ciclo getByUsuario(Long codigo);
}