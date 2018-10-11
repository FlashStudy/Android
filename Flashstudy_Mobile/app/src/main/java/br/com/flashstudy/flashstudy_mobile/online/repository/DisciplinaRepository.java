package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.clients.DisciplinaRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;

public class DisciplinaRepository {
    private DisciplinaRestClient restClient = new DisciplinaRestClient();

    public List<Disciplina> salvarLista(List<Disciplina> disciplinas) {
        try {
            return restClient.salvarLista(disciplinas);
        } catch (Exception e) {
            Log.i("ERRO SERV DISC", e.getMessage());
            return null;
        }
    }
}
