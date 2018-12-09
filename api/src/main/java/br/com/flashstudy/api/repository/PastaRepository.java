/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Pasta;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Igor
 */
public interface PastaRepository extends CrudRepository<Pasta, Long> {

    // Lista os flashcards do usuário
    @Query(value = "SELECT * FROM Pasta WHERE usuario_codigo = ?1", nativeQuery = true)
    List<Pasta> getByUsuario(Long codigo);

}
