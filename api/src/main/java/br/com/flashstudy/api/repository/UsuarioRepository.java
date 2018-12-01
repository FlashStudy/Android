package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Usuario;
import org.springframework.data.repository.CrudRepository;

//Repository do usuário (Comunicação com o BD)
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	// Procura o usuário pelo email
	Usuario findByEmail(String email);

}