package br.com.flashstudy.flashstudy_mobile.Util;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.adapter.model.DisciplinaListModel;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class ConversaoDeClasse {

    public static Usuario usuarioOffToUsuario(UsuarioOff usuarioOff){
        return new Usuario(usuarioOff.getCodigo(), usuarioOff.getNome(), usuarioOff.getEmail(), usuarioOff.getSenha());
    }

    public static Disciplina disciplinaListToDisciplina(DisciplinaListModel disciplinaListModel){
        return new Disciplina(disciplinaListModel.getCodigo(), disciplinaListModel.getNome(), disciplinaListModel.getAssuntos(), disciplinaListModel.getUsuario());
    }

    public static DisciplinaListModel disciplinaToDisciplinaList(Disciplina disciplina){
        return new DisciplinaListModel(disciplina.getCodigo(), disciplina.getNome(), disciplina.getAssuntos(), disciplina.getUsuario());
    }

    public static CronogramaOff cronogramaToCronogramaOff (Cronograma cronograma){
        return new CronogramaOff(cronograma.getCodigo(), cronograma.getInicio(), cronograma.getFim(), cronograma.getUsuario().getCodigo());
    }

    public static List<DisciplinaOff> listDisciplinaToListDisciplinaOff(List<Disciplina> disciplinas, long codigoCronograma){
        List<DisciplinaOff> offs = new ArrayList<>();
        for(Disciplina d : disciplinas){
            offs.add(new DisciplinaOff(d.getCodigo(), d.getNome(), d.getUsuario().getCodigo()));
        }

        return offs;
    }
}
