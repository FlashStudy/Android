package br.com.flashstudy.flashstudy_mobile.Util;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class ConversaoDeClasse {
    public static UsuarioOff usuarioToUsuarioOff(Usuario usuario) {
        return new UsuarioOff(usuario.getCodigo(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
    }

    public static Usuario usuarioOffToUsuario(UsuarioOff usuario) {
        return new Usuario(usuario.getCodigo(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
    }

    public static CronogramaOff cronogramaToCronogramaOff(Cronograma cronograma){
        return new CronogramaOff(cronograma.getCodigo(), cronograma.getInicio(), cronograma.getFim(), cronograma.getUsuario().getCodigo());
    }

    public static Cronograma cronogramaOffToCronograma(CronogramaOff off, Usuario usuario, List<Disciplina> disciplinas){
        return new Cronograma(off.getCodigo(), off.getInicio(), off.getFim(), usuario, disciplinas);
    }

    public static DisciplinaOff disciplinaToDisciplinaOff(Disciplina disciplina){
        return new DisciplinaOff(disciplina.getCodigo(), disciplina.getNome(), disciplina.getUsuario().getCodigo());
    }

    public static Disciplina disciplinaOffToDisciplina(DisciplinaOff off, Usuario usuario){
        return new Disciplina(off.getCodigo(), off.getNome(), usuario);
    }

}
