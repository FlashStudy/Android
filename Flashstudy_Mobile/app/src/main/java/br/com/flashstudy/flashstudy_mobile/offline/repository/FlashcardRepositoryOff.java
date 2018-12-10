package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;

public class FlashcardRepositoryOff {

    private Context context;

    public FlashcardRepositoryOff(Context context) {
        this.context = context;
    }

    public List<FlashcardOff> listar(long codigo) {
        try {
            return new Listar().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO LISTAR FLASH", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<FlashcardOff> listarPorPasta(long codigo) {
        try {
            return new ListarPorPasta().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO LISTAR FLASH", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<FlashcardOff> listarPorPasta(long codigo, String filtro) {
        try {
            return new Pesquisar(filtro).execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO LISTAR FLASH", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean salvar(FlashcardOff flashcardOff) {
        try {
            return new Salvar().execute(flashcardOff).get();
        } catch (Exception e) {
            Log.e("ERRO SALVAR FLASH", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean salvarLista(List<FlashcardOff> flashcardOffs) {
        try {
            return new SalvarLista().execute(flashcardOffs).get();
        } catch (Exception e) {
            Log.e("ERRO SALVAR FLASHs", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(FlashcardOff flashcardOff) {
        try {
            return new Deletar().execute(flashcardOff).get();
        } catch (Exception e) {
            Log.e("ERRO DELETAR FLASH", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarLista(List<FlashcardOff> flashcardOff) {
        try {
            return new DeletarLista().execute(flashcardOff).get();
        } catch (Exception e) {
            Log.e("ERRO DELETAR FLASH", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(FlashcardOff flashcardOff) {
        try {
            return new Atualizar().execute(flashcardOff).get();
        } catch (Exception e) {
            Log.e("ERRO ATUALIZAR FLASH ", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarLista(List<FlashcardOff> flashcardOffs) {
        try {
            return new AtualizarLista().execute(flashcardOffs).get();
        } catch (Exception e) {
            Log.e("ERRO ATUALIZAR FLASH ", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Listar extends AsyncTask<Long, Void, List<FlashcardOff>> {

        @Override
        protected List<FlashcardOff> doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).flashcardDao().getAllFlashcardsByUsuario(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO LISTAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class ListarPorPasta extends AsyncTask<Long, Void, List<FlashcardOff>> {

        @Override
        protected List<FlashcardOff> doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).flashcardDao().getAllFlashcardOffsByPasta(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO LISTAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Pesquisar extends AsyncTask<Long, Void, List<FlashcardOff>> {

        String filtro;

        public Pesquisar(String filtro) {
            this.filtro = filtro;
        }

        @Override
        protected List<FlashcardOff> doInBackground(Long... longs) {
            try {

                String filro2 = "%" + filtro + "%";

                return AppDatabase.getAppDatabase(context).flashcardDao().getAllFlashcardOffsByPastaPesquisa(longs[0], filro2);
            } catch (Exception e) {
                Log.e("ERRO LISTAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Salvar extends AsyncTask<FlashcardOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(FlashcardOff... flashcardOffs) {
            try {
                AppDatabase.getAppDatabase(context).flashcardDao().salvar(flashcardOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class SalvarLista extends AsyncTask<List<FlashcardOff>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<FlashcardOff>... lists) {
            try {
                AppDatabase.getAppDatabase(context).flashcardDao().salvarLista(lists[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Atualizar extends AsyncTask<FlashcardOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(FlashcardOff... flashcardOffs) {
            try {
                AppDatabase.getAppDatabase(context).flashcardDao().atualizar(flashcardOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ATUALIZAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class AtualizarLista extends AsyncTask<List<FlashcardOff>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<FlashcardOff>... lists) {
            try {
                AppDatabase.getAppDatabase(context).flashcardDao().atualizarLista(lists[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ATUALIZAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Deletar extends AsyncTask<FlashcardOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(FlashcardOff... flashcardOffs) {
            try {
                AppDatabase.getAppDatabase(context).flashcardDao().deletar(flashcardOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO DELETAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class DeletarLista extends AsyncTask<List<FlashcardOff>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<FlashcardOff>... lists) {
            try {
                AppDatabase.getAppDatabase(context).flashcardDao().deletarLista(lists[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO DELETAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

}
