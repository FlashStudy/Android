package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.clients.AssuntoRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Assunto;

public class AssuntoRepository {
    AssuntoRestClient client = new AssuntoRestClient();

    public List<Assunto> listar(long codigo) {
        try {
            return new Listar().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO REPO ASSUNTO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public List<Assunto> listarPorDisciplina(long codigo) {
        try {
            return new ListarPorDisciplina().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO REPO ASSUNTO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Assunto> salvar(List<Assunto> assuntos) {
        try {
            return new Salvar().execute(assuntos).get();
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

    public boolean atualizar(Assunto assunto) {
        try {
            return new Atualizar().execute(assunto).get();
        } catch (Exception e) {
            Log.e("ERRO REPO ASSUNTO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Listar extends AsyncTask<Long, Void, List<Assunto>> {

        @Override
        protected List<Assunto> doInBackground(Long... longs) {
            try {
                return client.findAll(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC ASSUNTO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class ListarPorDisciplina extends AsyncTask<Long, Void, List<Assunto>> {

        @Override
        protected List<Assunto> doInBackground(Long... longs) {
            try {
                return client.findByDisciplina(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC ASSUNTO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Salvar extends AsyncTask<List<Assunto>, Void, List<Assunto>> {

        @Override
        protected List<Assunto> doInBackground(List<Assunto>... lists) {
            try {
                List<Assunto> assuntos = lists[0];
                return client.salvar(assuntos);
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

    private class Atualizar extends AsyncTask<Assunto, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Assunto... assuntos) {
            try {
                return client.atualizar(assuntos[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC ASSUNTO", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }
}
