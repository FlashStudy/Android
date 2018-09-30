package com.android.crud.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import com.android.crud.model.Usuario;
import com.android.crud.repository.UsuarioRepository;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping(value = "findall", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "findid/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<?> find(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<>(usuarioRepository.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Cadastrar
	@PostMapping(value = "/save", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<?> cadastro(@Valid @RequestBody Usuario usuario) {

		return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.OK);
	}

	// Login
	@PostMapping(value = "/login", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<?> login(@RequestBody Usuario usuario) {
		try {
			return new ResponseEntity<>(usuarioRepository.findByEmail(usuario.getEmail()), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	// Verifica se o email já está cadastrado
	@GetMapping(value = "/find/{email}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<?> verifica(@PathVariable("email") String email) {
		return new ResponseEntity<>(usuarioRepository.findByEmail(email), HttpStatus.OK);

	}

	@PutMapping(value = "/atualizar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {

		try {
			return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/delete/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") int id) {

		try {
			usuarioRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
