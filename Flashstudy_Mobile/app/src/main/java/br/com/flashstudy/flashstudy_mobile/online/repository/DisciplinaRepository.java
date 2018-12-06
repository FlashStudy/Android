package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.clients.DisciplinaRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;

public class DisciplinaRepository {

    private DisciplinaRestClient restClient = new DisciplinaRestClient();

    public List<Disciplina> listar(long codigo) {
        try {
            return new Listar().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO REPO SERV", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Disciplina> salvarLista(List<Disciplina> disciplinas) {
        try {
            return new SalvarLista().execute(disciplinas).get();
        } catch (Exception e) {
            Log.e("ERRO REPO SERV", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Disciplina salvar(Disciplina disciplina) {
        try {
            return new Salvar().execute(disciplina).get();
        } catch (Exception e) {
            Log.e("ERRO REPO SERV", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean atualizar(Disciplina disciplina) {
        try {
            return new Atualizar().execute(disciplina).get();
        } catch (Exception e) {
            Log.e("ERRO REPO SERV", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(long codigo) {
        try {
            return new Deletar().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO REPO SERV", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Listar extends AsyncTask<Long, Void, List<Disciplina>> {

        @Override
        protected List<Disciplina> doInBackground(Long... longs) {
            try {
                return restClient.listar(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC SERV", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class SalvarLista extends AsyncTask<List<Disciplina>, Void, List<Disciplina>> {

        @Override
        protected List<Disciplina> doInBackground(List<Disciplina>... lists) {
            try {
                List<Disciplina> disciplinas = lists[0];
                return restClient.salvarLista(disciplinas);
            } catch (Exception e) {
                Log.e("ERRO ASYNC SERV", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Salvar extends AsyncTask<Disciplina, Void, Disciplina> {

        @Override
        protected Disciplina doInBackground(Disciplina... disciplinas) {
            try {
                return restClient.salvar(disciplinas[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC SERV", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Deletar extends AsyncTask<Long, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Long... longs) {
            try {
                return restClient.deletar(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC SERV", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Atualizar extends AsyncTask<Disciplina, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Disciplina... disciplinas) {
            try {
                return restClient.atualizar(disciplinas[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC SERV", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

}
