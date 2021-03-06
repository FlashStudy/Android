package com.android.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import com.android.crud.model.Assunto;
import com.android.crud.model.Disciplina;
import com.android.crud.repository.DisciplinaRepository;

//Controller de Disciplinas
@RestController
@RequestMapping("disciplina")
public class DisciplinaController {

	// Operações no BD das disciplinas
	@Autowired
	private DisciplinaRepository disciplinaRepository;

	// Lista as disciplinas de um usuário
	@GetMapping(value = "findallbyuser/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> findAll(@PathVariable("codigo") Long codigo) {
		return new ResponseEntity<>(disciplinaRepository.getByUsuarioCodigo(codigo), HttpStatus.OK);
	}

	// Salva/atualiza a disciplina
	@PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> salvar(@RequestBody Disciplina disciplina) {

		Disciplina d = new Disciplina(disciplina.getCodigo(), disciplina.getNome(), disciplina.getUsuario());

		List<Assunto> assuntos = disciplina.getAssuntos();

		for (Assunto a : assuntos) {
			a.setUsuario(disciplina.getUsuario());
			d.addAssunto(a);
		}

		return new ResponseEntity<>(disciplinaRepository.save(d), HttpStatus.OK);
	}
	
        // Salva/atualiza a disciplina
	@PostMapping(value = "/salvarLista", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> salvarLista(@RequestBody List<Disciplina> disciplinas) {
            for(Disciplina d : disciplinas){
                disciplinaRepository.save(d);
            }
            return new ResponseEntity<>(HttpStatus.OK);
	}
        
	// Deleta através do código da disciplina
	@DeleteMapping(value = "/deleta/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> deletar(@PathVariable("codigo") Long codigo) {
		disciplinaRepository.deleteById(codigo);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}