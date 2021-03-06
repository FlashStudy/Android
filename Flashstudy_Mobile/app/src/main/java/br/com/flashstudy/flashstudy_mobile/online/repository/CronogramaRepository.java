package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.os.AsyncTask;
import android.util.Log;

import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.online.clients.CronogramaRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;

public class CronogramaRepository {

    CronogramaRestClient cronogramaRestClient = new CronogramaRestClient();

    public Cronograma salvar(Cronograma cronograma){
        try{
            return new Salvar().execute(cronograma).get();
        }catch (Exception e){
            Log.e("ERRO REPO CRONO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Cronograma procurar(long codigo){
        try{
            return new Procurar().execute(codigo).get();
        }catch (Exception e){
            Log.e("ERRO REPO CRONO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private class Salvar extends AsyncTask<Cronograma, Void, Cronograma>{

        @Override
        protected Cronograma doInBackground(Cronograma... cronogramas) {
            try{
                return cronogramaRestClient.salvar(cronogramas[0]);
            }catch (Exception e){
                Log.e("ERRO ASYNC CRONO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Procurar extends AsyncTask<Long, Void, Cronograma>{

        @Override
        protected Cronograma doInBackground(Long... longs) {
            try{
                return cronogramaRestClient.buscarPorUsuarioCodigo(longs[0]);
            }catch (Exception e){
                Log.e("ERRO ASYNC CRONO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }


}
