package br.com.flashstudy.api.controller;

import br.com.flashstudy.api.model.Assunto;
import br.com.flashstudy.api.model.Cronograma;
import br.com.flashstudy.api.model.Disciplina;
import br.com.flashstudy.api.repository.CronogramaRepository;
import br.com.flashstudy.api.repository.DisciplinaRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?> salvar(@RequestBody Cronograma cronograma) {

        System.out.println(cronograma.toString());

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
    @GetMapping(path = "/atual/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> lista(@PathVariable("codigo") long codigo) {

        return new ResponseEntity<>(cronogramaRepository.getByUsuario(codigo), HttpStatus.OK);

    }

}
