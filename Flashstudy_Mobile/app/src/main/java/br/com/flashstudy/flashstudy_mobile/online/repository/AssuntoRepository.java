package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.os.AsyncTask;
import android.util.Log;

import br.com.flashstudy.flashstudy_mobile.online.clients.AssuntoRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Assunto;

public class AssuntoRepository {
    AssuntoRestClient client = new AssuntoRestClient();

    public Assunto salvar(Assunto assunto) {
        try {
            return new Salvar().execute(assunto).get();
        } catch (Exception e) {
            Log.e("ERRO REPO ASSUNTO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletar(long codigo) {
        try {
            return new Deletar().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO REPO ASSUNTO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Salvar extends AsyncTask<Assunto, Void, Assunto> {

        @Override
        protected Assunto doInBackground(Assunto... assuntos) {
            try {
                return client.salvar(assuntos[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC ASSUNTO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Deletar extends AsyncTask<Long, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Long... longs) {
            try {
                return client.delete(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC ASSUNTO", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }
}
