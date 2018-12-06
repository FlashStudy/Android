package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;

public class AssuntoRepositoryOff {

    private Context context;

    public AssuntoRepositoryOff(Context context) {
        this.context = context;
    }

    public boolean salvarLista(List<AssuntoOff> assuntoOffs) {
        try {
            return new SalvarLista().execute(assuntoOffs).get();
        } catch (Exception e) {
            Log.e("ERRO SALVAR ASSUNTOS", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarLista(List<AssuntoOff> assuntoOffs) {
        try {
            return new AtualizarLista().execute(assuntoOffs).get();
        } catch (Exception e) {
            Log.e("ERRO ATUALIZAR ASSUNTOS", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<AssuntoOff> listarPorDisciplinaCodigo(long codigo) {
        try {
            return new ListarPorDisciplina().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO LISTAR ASSUNTOS", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public List<AssuntoOff> listar() {
        try {
            return new Listar().execute().get();
        } catch (Exception e) {
            Log.e("ERRO LISTAR ASSUNTOS", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean atualizar(AssuntoOff assuntoOff) {
        try {
            return new Atualizar().execute(assuntoOff).get();
        } catch (Exception e) {
            Log.e("ERRO ATUALIZAR ASSUNTO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(AssuntoOff assuntoOff) {
        try {
            return new Deletar().execute(assuntoOff).get();
        } catch (Exception e) {
            Log.e("ERRO DELETAR ASSUNTOS", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class SalvarLista extends AsyncTask<List<AssuntoOff>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<AssuntoOff>... lists) {
            List<AssuntoOff> assuntoOffs = lists[0];
            try {
                AppDatabase.getAppDatabase(context).assuntoDao().salvarLista(assuntoOffs);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }

        }
    }

    private class AtualizarLista extends AsyncTask<List<AssuntoOff>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<AssuntoOff>... lists) {
            List<AssuntoOff> assuntoOffs = lists[0];
            try {
                AppDatabase.getAppDatabase(context).assuntoDao().atualizarLista(assuntoOffs);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ATUALIZAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }

        }
    }

    private class ListarPorDisciplina extends AsyncTask<Long, Void, List<AssuntoOff>> {

        @Override
        protected List<AssuntoOff> doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).assuntoDao().getAllAssuntosByDisciplina(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO LISTAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Listar extends AsyncTask<Void, Void, List<AssuntoOff>> {

        @Override
        protected List<AssuntoOff> doInBackground(Void... voids) {
            try {
                return AppDatabase.getAppDatabase(context).assuntoDao().getAll();
            } catch (Exception e) {
                Log.e("ERRO LISTAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Atualizar extends AsyncTask<AssuntoOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(AssuntoOff... assuntoOffs) {
            try {
                AppDatabase.getAppDatabase(context).assuntoDao().atualizar(assuntoOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ATUALIZAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Deletar extends AsyncTask<AssuntoOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(AssuntoOff... assuntoOffs) {
            try {
                AppDatabase.getAppDatabase(context).assuntoDao().deletar(assuntoOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO DELETAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }
}
