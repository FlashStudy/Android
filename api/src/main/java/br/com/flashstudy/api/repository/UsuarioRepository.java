package br.com.flashstudy.api.repository;

import br.com.flashstudy.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

//Repository do Usuario (Comunicação com o BD)
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

}
