package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;

public class CronogramaRepositoryOff {
    private Context context;

    public CronogramaRepositoryOff(Context context) {
        this.context = context;
    }


    public boolean salvar(CronogramaOff cronogramaOff) {
        try {
            boolean res = new Salvar().execute(cronogramaOff).get();
            if (res) {
                return new DisciplinaRepositoryOff(context).salvarLista(cronogramaOff.getDisciplinas());
            }
            return res;
        } catch (Exception e) {
            Log.e("ERRO SALVAR CRONOGRAMA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public CronogramaOff buscarPorUsario(long codigo) {
        try {
            return new BuscarPorUsuario().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO BUSCAR CRONOGRAMA", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean atualizar(CronogramaOff cronogramaOff) {
        try {
            List<DisciplinaOff> novasDisciplinas = new ArrayList<>();
            for (DisciplinaOff d : cronogramaOff.getDisciplinas()) {
                if (d.getCodigo() == 0) {
                    novasDisciplinas.add(d);
                }
            }

            boolean res = new Atualizar().execute(cronogramaOff).get();
            if (res) {
                return new DisciplinaRepositoryOff(context).salvarLista(novasDisciplinas);
            }

            return res;
        } catch (Exception e) {
            Log.e("ERRO UPDATE CRONOGRAMA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Salvar extends AsyncTask<CronogramaOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CronogramaOff... cronogramaOffs) {
            try {
                AppDatabase.getAppDatabase(context).cronogramaDao().salvar(cronogramaOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class BuscarPorUsuario extends AsyncTask<Long, Void, CronogramaOff> {

        @Override
        protected CronogramaOff doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).cronogramaDao().getCronogramaByUsuario(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO BUSCAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Atualizar extends AsyncTask<CronogramaOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CronogramaOff... cronogramaOffs) {
            try {
                AppDatabase.getAppDatabase(context).cronogramaDao().atualizar(cronogramaOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ATUALIZAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }
}
