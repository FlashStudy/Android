package com.android.crud.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.android.crud.model.Assunto;
import com.android.crud.model.Cronograma;
import com.android.crud.model.Disciplina;
import com.android.crud.repository.CronogramaRepository;
import com.android.crud.repository.DisciplinaRepository;

//Controller do Cronograma
@RestController
@RequestMapping("cronograma")
public class CronogramaController {

	// Operações no BD do cronograma
	@Autowired
	CronogramaRepository cronogramaRepository;

	// Operações no BD das disciplinas
	@Autowired
	DisciplinaRepository disciplinaRepository;

	// Salva/atualiza o cronograma do usuário
	@PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> salvar(@RequestBody Cronograma cronograma) {

		Cronograma c = new Cronograma(cronograma.getCodigo(), cronograma.getInicio(), cronograma.getFim(),
				cronograma.getUsuario());

		c.setDisciplinas(new HashSet<>());

		for (Disciplina disciplina : cronograma.getDisciplinas()) {
			disciplina.setUsuario(cronograma.getUsuario());

			Set<Assunto> assuntos = disciplina.getAssuntos();

			if (assuntos != null) {
				disciplina.setAssuntos(new HashSet<>());
				for (Assunto assunto : assuntos) {
					disciplina.addAssunto(assunto);
				}
			}

			c.addDisciplina(disciplina);
		}

		return new ResponseEntity<>(cronogramaRepository.save(c), HttpStatus.OK);

	}

	// Busca o cronograma atual do usuário
	@GetMapping(path = "/atual/{codigo}")
	public ResponseEntity<?> lista(@PathVariable("codigo") long codigo) {

		return new ResponseEntity<>(cronogramaRepository.getByUsuarioCodigo(codigo), HttpStatus.OK);

	}

}