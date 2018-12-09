/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flashstudy.api.controller;

import br.com.flashstudy.api.model.Pasta;
import br.com.flashstudy.api.repository.PastaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Igor
 */

@RestController
@RequestMapping("pasta")
public class PastaController {
    // Operações no BD dos pastas
    @Autowired
    PastaRepository pastaRepository;

    @GetMapping(value = "findallbyuser/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> findAll(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(pastaRepository.getByUsuario(codigo), HttpStatus.OK);
    }

    // Salva/atualiza o pasta
    @PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> salvar(@RequestBody Pasta pasta) {

        return new ResponseEntity<>(pastaRepository.save(pasta), HttpStatus.OK);

    }

    // Deleta através do código do pasta
    @DeleteMapping(value = "/delete/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("codigo") Long codigo) {

        try {
            pastaRepository.deleteById(codigo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
