package com.android.crud.repository;

import org.springframework.data.repository.CrudRepository;

import com.android.crud.model.Assunto;

//Repository dos assuntos (Comunicação com o BD)
public interface AssuntoRepository extends CrudRepository<Assunto, Long> {

}