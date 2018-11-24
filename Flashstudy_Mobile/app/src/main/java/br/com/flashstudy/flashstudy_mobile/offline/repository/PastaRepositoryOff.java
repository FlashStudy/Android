package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;

public class PastaRepositoryOff {

    private Context context;

    public PastaRepositoryOff(Context context) {
        this.context = context;
    }

    public List<PastaOff> listar(long codigo) {
        try {
            return new Listar().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO LISTAR PASTAS", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean salvar(PastaOff pastaOff) {
        try {
            return new Salvar().execute(pastaOff).get();
        } catch (Exception e) {
            Log.e("ERRO SALVAR PASTA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Salvar extends AsyncTask<PastaOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(PastaOff... pastaOffs) {
            try {
                AppDatabase.getAppDatabase(context).pastaDao().salvar(pastaOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class AtualizarLista extends AsyncTask<PastaOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(PastaOff... pastaOffs) {
            try {
                AppDatabase.getAppDatabase(context).pastaDao().atualizar(pastaOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ATUALIZAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Listar extends AsyncTask<Long, Void, List<PastaOff>> {

        @Override
        protected List<PastaOff> doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).pastaDao().getAllPastasByUsuario(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO LISTAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}
