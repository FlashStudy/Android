package com.android.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.android.crud.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario findByEmail(String email);

}
