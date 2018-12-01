package br.com.flashstudy.api.controller;

import br.com.flashstudy.api.model.Assunto;
import br.com.flashstudy.api.repository.AssuntoRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Controller de Assuntos
@Component
@RestController
@RequestMapping(value = "/assunto")
public class AssuntoController {

	@Autowired
	private AssuntoRepository assuntoRepository;

	// Deleta os assuntos selecinados
	@DeleteMapping(value = "/deleta", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> deletar(@RequestBody List<Assunto> assuntosSelecionados) {

		for (Assunto assunto : assuntosSelecionados) {
			assuntoRepository.delete(assunto);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}