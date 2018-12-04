package br.com.flashstudy.api.controller;

//Controller de Disciplinas
import br.com.flashstudy.api.model.Assunto;
import br.com.flashstudy.api.model.Disciplina;
import br.com.flashstudy.api.repository.DisciplinaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("disciplina")
public class DisciplinaController {

    // Operações no BD das disciplinas
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    // Lista as disciplinas de um usuário
    @GetMapping(value = "findallbyuser/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> findAll(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(disciplinaRepository.getByUsuario(codigo), HttpStatus.OK);
    }

    // Salva/atualiza a disciplina
    @PostMapping(value = "/salvarLista", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> salvarLista(@RequestBody List<Disciplina> disciplinas) {

        List<Disciplina> discs = new ArrayList<>();

        for (Disciplina d : disciplinas) {
            Disciplina disc = new Disciplina(d.getCodigo(), d.getNome(), d.getUsuario());

            Set<Assunto> assuntos = d.getAssuntos();

            for (Assunto a : assuntos) {
                a.setUsuario(d.getUsuario());
                disc.addAssunto(a);
            }
            discs.add(d);
        }

        return new ResponseEntity<>(disciplinaRepository.saveAll(discs), HttpStatus.OK);
    }

    // Salva/atualiza a disciplina
    @PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> salvar(@RequestBody Disciplina disciplina) {
        Disciplina disc = new Disciplina(disciplina.getCodigo(), disciplina.getNome(), disciplina.getUsuario());
        Set<Assunto> assuntos = disciplina.getAssuntos();
        for (Assunto a : assuntos) {
            a.setUsuario(disciplina.getUsuario());
            disc.addAssunto(a);
        }
        return new ResponseEntity<>(disciplinaRepository.save(disc), HttpStatus.OK);
    }

    // Deleta através do código da disciplina
    @DeleteMapping(value = "/delete/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> deletar(@PathVariable("codigo") Long codigo) {
        disciplinaRepository.deleteById(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
