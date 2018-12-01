package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;

public class CicloRepositoryOff {

    private Context context;
    private DisciplinaRepositoryOff disciplinaRepositoryOff;
    private HorarioRepositoryOff horarioRepositoryOff;


    public CicloRepositoryOff(Context context) {
        this.context = context;
        disciplinaRepositoryOff = new DisciplinaRepositoryOff(context);
        horarioRepositoryOff = new HorarioRepositoryOff(context);
    }

    public CicloOff buscarPorUsario(long codigo) {
        try {
            return new BuscarPorUsuario().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO BUSCAR CICLO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private class BuscarPorUsuario extends AsyncTask<Long, Void, CicloOff> {

        @Override
        protected CicloOff doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).cicloDao().getCicloByUsuario(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO BUSCAR ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean salvar(CicloOff cicloOff, List<String> dias) {

        long codigo_usuario = Util.getLocalUserCodigo(context);

        if (cicloOff.getCodigo() != 0) {
            try {
                new Deletar().execute(cicloOff).get();
            } catch (Exception e) {
                Log.e("ERRO DELETAR CICLO", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        try {
            Random rand = new Random();

            CicloOff c = new CicloOff(cicloOff.getNumMaterias(), Util.getLocalUserCodigo(context));

            List<DisciplinaOff> disciplinas = disciplinaRepositoryOff.listar(codigo_usuario);

            int arrLength = disciplinas.size();

            List<HorarioOff> horarios = new ArrayList<>();

            if (cicloOff.getHorarios() != null) {
                horarioRepositoryOff.deletarLista(cicloOff.getHorarios());
            }

            for (int i = 0; i < dias.size(); i++) {
                for (int j = 0; j < cicloOff.getNumMaterias(); j++) {
                    HorarioOff horarioOff = new HorarioOff(j + 1, disciplinas.get(rand.nextInt(arrLength)).getCodigo(), Util.getLocalUserCodigo(context), dias.get(i));
                    horarios.add(horarioOff);
                }
            }

            Log.i("HORARIOS", horarios.toString());


            if (horarioRepositoryOff.salvarLista(horarios)) {
                boolean res = new Salvar().execute(c).get();
                return res;
            } else {
                return false;
            }

        } catch (Exception e) {
            Log.e("ERRO SALVAR CICLO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Deletar extends AsyncTask<CicloOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CicloOff... cicloOffs) {
            try {
                AppDatabase.getAppDatabase(context).cicloDao().deletar(cicloOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO DELETAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Salvar extends AsyncTask<CicloOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CicloOff... cicloOffs) {
            try {
                AppDatabase.getAppDatabase(context).cicloDao().salvar(cicloOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

}
