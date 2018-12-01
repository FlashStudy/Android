package br.com.flashstudy.api.controller;

//Controller de Disciplinas
import br.com.flashstudy.api.model.Assunto;
import br.com.flashstudy.api.model.Disciplina;
import br.com.flashstudy.api.repository.DisciplinaRepository;
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
    @PostMapping(value = "/salvar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> salvar(@RequestBody Disciplina disciplina) {

        Disciplina d = new Disciplina(disciplina.getCodigo(), disciplina.getNome(), disciplina.getUsuario());

        Set<Assunto> assuntos = disciplina.getAssuntos();

        for (Assunto a : assuntos) {
            a.setUsuario(disciplina.getUsuario());
            d.addAssunto(a);
        }

        return new ResponseEntity<>(disciplinaRepository.save(d), HttpStatus.OK);
    }
    
    // Deleta através do código da disciplina
    @DeleteMapping(value = "/deleta/{codigo}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> deletar(@PathVariable("codigo") Long codigo) {
        disciplinaRepository.deleteById(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
