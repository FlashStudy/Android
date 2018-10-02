package com.android.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import com.android.crud.model.Flashcard;
import com.android.crud.repository.FlashcardRepository;

@RestController
@RequestMapping("flashcard")
public class FlashcardController {

	// Operações no BD dos flashcards
	@Autowired
	FlashcardRepository flashcardRepository;

	@GetMapping(value = "findallbyuser/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> findAll(@PathVariable("codigo") Long codigo) {
		return new ResponseEntity<>(flashcardRepository.getByUsuarioCodigo(codigo), HttpStatus.OK);
	}

	// Salva/atualiza o flashcard
	@PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> salvar(@RequestBody Flashcard flashcard) {

		return new ResponseEntity<>(flashcardRepository.save(flashcard), HttpStatus.OK);

	}

	// Deleta através do código do flashcard
	@DeleteMapping(value = "/delete/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("codigo") Long codigo) {

		try {
			flashcardRepository.deleteById(codigo);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}