package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;

public class FlashcardRepositoryOff {
    private Context context;

    public List<FlashcardOff> listar(long codigo, Context context) {
        this.context = context;
        try {
            return new ListaFlashcardsAsync().execute(codigo).get();
        } catch (Exception e) {
            Log.i("ERRO CONSULT SERV", e.getMessage());
            return null;
        }
    }

    public boolean salvar(FlashcardOff flashcardOff, Context context) {
        this.context = context;
        try {
            return new SalvaFlashcardAsync().execute(flashcardOff).get();
        } catch (Exception e) {
            Log.i("ERRO AO SALVAR", e.getMessage());
            return false;
        }
    }

    public boolean deletar(FlashcardOff flashcardOff, Context context) {
        this.context = context;
        try {
            return new DeletarFlashcardAsync().execute(flashcardOff).get();
        } catch (Exception e) {
            Log.i("ERRO AO DELETAR", e.getMessage());
            return false;
        }
    }

    private class ListaFlashcardsAsync extends AsyncTask<Long, Void, List<FlashcardOff>> {


        @Override
        protected List<FlashcardOff> doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).flashcardDao().getAllFlashcardsByUsuario(longs[0]);
            } catch (Exception e) {
                Log.i("ERRO NA CONSULTA", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<FlashcardOff> flashcardOffs) {
            super.onPostExecute(flashcardOffs);
        }
    }

    private class SalvaFlashcardAsync extends AsyncTask<FlashcardOff, Void, Boolean> {


        @Override
        protected Boolean doInBackground(FlashcardOff... flashcardOffs) {
            try {
                AppDatabase.getAppDatabase(context).flashcardDao().insert(flashcardOffs[0]);

                return true;
            } catch (Exception e) {
                Log.i("ERRO SALVAR BANCO", e.getMessage());
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    private class DeletarFlashcardAsync extends AsyncTask<FlashcardOff, Void, Boolean> {


        @Override
        protected Boolean doInBackground(FlashcardOff... flashcardOffs) {
            try {
                AppDatabase.getAppDatabase(context).flashcardDao().delete(flashcardOffs[0]);
                return true;
            } catch (Exception e) {
                Log.i("ERRO DELETAR NO BANCO", e.getMessage());
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
