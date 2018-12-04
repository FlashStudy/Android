package br.com.flashstudy.api.controller;

import br.com.flashstudy.api.model.*;
import br.com.flashstudy.api.repository.CicloRepository;
import br.com.flashstudy.api.repository.DisciplinaRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

//Controller do ciclo
@Component
@RestController
@RequestMapping(value = "/ciclo")
public class CicloController {

    // Operações no BD do ciclo
    @Autowired
    private CicloRepository cicloRepository;

    // Operações no BD das disciplinas
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    // Solicita o ciclo atual
    @GetMapping(path = "/atual/{codigo}")
    public ResponseEntity<?> getAtual(@PathVariable("codigo") long codigo) {
        return new ResponseEntity<>(cicloRepository.getByUsuario(codigo), HttpStatus.OK);
    }

    // Salva/atualiza o cronograma do usuário
    @PostMapping(path = "/salvar")
    public ResponseEntity<?> salvar(@RequestBody Ciclo ciclo) {
        Random rand = new Random();

        Usuario usuario = ciclo.getUsuario();

        Ciclo c;

        if (ciclo.getCodigo() == 0) {
            c = new Ciclo(ciclo.getNumMaterias(), usuario);
        } else {
            c = new Ciclo(ciclo.getCodigo(), ciclo.getNumMaterias(), usuario);
        }

        Set<DiaDaSemana> dias = ciclo.getDias();

        List<Disciplina> disciplinas = disciplinaRepository.getByUsuario(usuario.getCodigo());

        int arrLength = disciplinas.size();

        c.setDias(new HashSet<>());

        for (DiaDaSemana dia : dias) {
            for (int i = 0; i < ciclo.getNumMaterias(); i++) {

                Horario horario = new Horario(i, disciplinas.get(rand.nextInt(arrLength)), usuario);
                dia.addHorario(horario);
            }
            dia.setUsuario(usuario);
            c.addDiaDaSemana(dia);
        }

        return new ResponseEntity<>(cicloRepository.save(c), HttpStatus.OK);

    }
}
