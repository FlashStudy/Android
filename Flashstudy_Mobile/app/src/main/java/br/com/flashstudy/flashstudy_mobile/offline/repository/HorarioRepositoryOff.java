package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;

public class HorarioRepositoryOff {

    private Context context;

    public HorarioRepositoryOff(Context context) {
        this.context = context;
    }

    public List<HorarioOff> listar(long codigo) {
        try {
            return new Listar().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO LISTAR HORARIOS", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletarLista(long usuario_codigo) {
        try {
            return new DeletarLista().execute(usuario_codigo).get();
        } catch (Exception e) {
            Log.e("ERRO DELETAR HORARIOS", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean salvarLista(List<HorarioOff> horarioOffs) {
        try {
            return new SalvarLista().execute(horarioOffs).get();
        } catch (Exception e) {
            Log.e("ERRO SALVAR LISTA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Listar extends AsyncTask<Long, Void, List<HorarioOff>> {

        @Override
        protected List<HorarioOff> doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).horarioDao().getAllHorariosByUsuario(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO LISTAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class DeletarLista extends AsyncTask<Long, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Long... longs) {
            try {
                AppDatabase.getAppDatabase(context).horarioDao().deleteHorariosFromUsuario(longs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO DELETAR LIST ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class SalvarLista extends AsyncTask<List<HorarioOff>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<HorarioOff>... lists) {
            List<HorarioOff> horarioOffs = lists[0];

            try {
                AppDatabase.getAppDatabase(context).horarioDao().salvarLista(horarioOffs);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR LISTA ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }

        }
    }
}
