package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;

public class DisciplinaRepositoryOff {

    private Context context;

    public DisciplinaRepositoryOff(Context context) {
        this.context = context;
    }

    public List<DisciplinaOff> listar(long codigo) {
        try {
            return new Listar().execute(codigo).get();
        } catch (Exception e) {
            Log.i("ERRO NA CONSULTA", e.getMessage());
            return null;
        }
    }

    public boolean salvarLista(List<DisciplinaOff> disciplinaOffs) {
        try{
            return new SalvarLista().execute(disciplinaOffs).get();
        }catch (Exception e){
            Log.e("ERRO SALVAR LISTA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean atualizar(DisciplinaOff disciplinaOff) {
        try {
            return new Atualizar().execute(disciplinaOff).get();
        } catch (Exception e) {
            Log.e("ERRO ATUALIZAR DISC", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(DisciplinaOff disciplinaOff) {
        try {
            return new Deletar().execute(disciplinaOff).get();
        } catch (Exception e) {
            Log.e("ERRO DELETAR DISCIPLINA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Listar extends AsyncTask<Long, Void, List<DisciplinaOff>> {

        @Override
        protected List<DisciplinaOff> doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).disciplinaDao().getDisciplinaOffsByUsuario(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO LISTAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class SalvarLista extends AsyncTask<List<DisciplinaOff>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<DisciplinaOff>... lists) {
            List<DisciplinaOff> disciplinaOffs = lists[0];

            try {
                AppDatabase.getAppDatabase(context).disciplinaDao().salvarLista(disciplinaOffs);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR LISTA ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }

        }
    }

    private class Atualizar extends AsyncTask<DisciplinaOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(DisciplinaOff... disciplinaOffs) {
            try {
                AppDatabase.getAppDatabase(context).disciplinaDao().atualizar(disciplinaOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ATUALIZAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Deletar extends AsyncTask<DisciplinaOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(DisciplinaOff... disciplinaOffs) {
            try {
                AppDatabase.getAppDatabase(context).disciplinaDao().deletar(disciplinaOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO DELETAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

}
