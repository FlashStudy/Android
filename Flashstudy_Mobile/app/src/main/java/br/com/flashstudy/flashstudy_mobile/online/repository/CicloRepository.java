package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.os.AsyncTask;
import android.util.Log;

import br.com.flashstudy.flashstudy_mobile.online.clients.CicloRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Ciclo;

public class CicloRepository {
    CicloRestClient cicloRestClient = new CicloRestClient();

    public Ciclo salvar(Ciclo ciclo){
        try{
            return new Salvar().execute(ciclo).get();
        }catch (Exception e){
            Log.e("ERRO REPO CRONO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Ciclo procurar(long codigo){
        try{
            return new Procurar().execute(codigo).get();
        }catch (Exception e){
            Log.e("ERRO REPO CRONO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private class Salvar extends AsyncTask<Ciclo, Void, Ciclo> {

        @Override
        protected Ciclo doInBackground(Ciclo... ciclos) {
            try{
                return cicloRestClient.salvar(ciclos[0]);
            }catch (Exception e){
                Log.e("ERRO ASYNC CRONO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Procurar extends AsyncTask<Long, Void, Ciclo>{

        @Override
        protected Ciclo doInBackground(Long... longs) {
            try{
                return cicloRestClient.buscarPorUsuarioCodigo(longs[0]);
            }catch (Exception e){
                Log.e("ERRO ASYNC CRONO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}
