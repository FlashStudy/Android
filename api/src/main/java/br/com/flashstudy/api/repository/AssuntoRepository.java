package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Assunto;
import org.springframework.data.repository.CrudRepository;

//Repository dos Assuntos (Comunicação com o BD)
public interface AssuntoRepository extends CrudRepository<Assunto, Long> {

}
