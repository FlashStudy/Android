package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.clients.PastaRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Pasta;

public class PastaRepository {
    private PastaRestClient pastaRestClient = new PastaRestClient();

    public List<Pasta> listarPastas(long codigo) {
        try {
            return new ListaPastasAsync().execute(codigo).get();
        } catch (Exception e) {
            Log.i("ERRO CONSULT SERV", e.getMessage());
            return null;
        }
    }

    public Pasta salvar(Pasta pasta) {
        try {
            return new SalvaPastaAsync().execute(pasta).get();
        } catch (Exception e) {
            Log.i("ERRO AO SALVAR", e.getMessage());
            return null;
        }
    }

    public boolean deletar(long codigo) {
        try {
            return new DeletarPastaAsync().execute(codigo).get();
        } catch (Exception e) {
            Log.i("ERRO AO DELETAR", e.getMessage());
            return false;
        }
    }

    private class ListaPastasAsync extends AsyncTask<Long, Void, List<Pasta>> {

        @Override
        protected List<Pasta> doInBackground(Long... longs) {
            try{
                return pastaRestClient.findAll(longs[0]);
            }catch (Exception e){
                Log.e("ERRO ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

    }

    private class SalvaPastaAsync extends AsyncTask<Pasta, Void, Pasta> {

        @Override
        protected Pasta doInBackground(Pasta... pastas) {
            try{
                return pastaRestClient.salvar(pastas[0]);
            }catch (Exception e){
                Log.e("ERRO ASYNC SERV", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

    }

    private class DeletarPastaAsync extends AsyncTask<Long, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Long... longs) {
            try{
                return pastaRestClient.deletar(longs[0]);
            }catch (Exception e){
                Log.e("ERRO ASYNC SERV", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}
