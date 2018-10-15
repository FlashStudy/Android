package br.com.flashstudy.api.controller;

import br.com.flashstudy.api.model.Assunto;
import br.com.flashstudy.api.model.Cronograma;
import br.com.flashstudy.api.model.Disciplina;
import br.com.flashstudy.api.repository.CronogramaRepository;
import br.com.flashstudy.api.repository.DisciplinaRepository;
import java.util.ArrayList;
import java.util.List;
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
    @PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> salvar(@RequestBody Cronograma cronograma) {

        System.out.println(cronograma.toString());

        Cronograma c = new Cronograma(cronograma.getCodigo(), cronograma.getInicio(), cronograma.getFim(),
                cronograma.getUsuario());

        c.setDisciplinas(new ArrayList<>());

        for (Disciplina disciplina : cronograma.getDisciplinas()) {
            disciplina.setUsuario(cronograma.getUsuario());

            List<Assunto> assuntos = disciplina.getAssuntos();

            if (assuntos != null) {
                disciplina.setAssuntos(new ArrayList<>());
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
