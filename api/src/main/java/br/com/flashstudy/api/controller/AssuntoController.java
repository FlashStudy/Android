package br.com.flashstudy.api.controller;

import br.com.flashstudy.api.model.Assunto;
import br.com.flashstudy.api.repository.AssuntoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

//Controller de Assuntos
@Component
@RestController
@RequestMapping(value = "/assunto")
public class AssuntoController {
    
    @Autowired
    private AssuntoRepository assuntoRepository;

    // Deleta os assuntos selecinados
    @PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> salvar(@RequestBody Assunto assunto) {
        return new ResponseEntity<>(assuntoRepository.save(assunto), HttpStatus.OK);
    }
    
    // Deleta os assuntos selecinados
    @DeleteMapping(value = "/delete/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> deletar(@PathVariable("codigo") long codigo) {
        
        assuntoRepository.deleteById(codigo);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
