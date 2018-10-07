package br.com.flashstudy.flashstudy_mobile.online.repository;

import br.com.flashstudy.flashstudy_mobile.online.clients.CronogramaRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;

public class CronogramaRepository {

    CronogramaRestClient cronogramaRestClient = new CronogramaRestClient();

    public Cronograma buscar(long codigo){

        return cronogramaRestClient.buscarPorUsuarioCodigo(codigo);
    }

    public Cronograma salvar(Cronograma cronograma){
        return cronogramaRestClient.salvar(cronograma);
    }
}
