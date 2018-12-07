package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Ciclo;
import br.com.flashstudy.flashstudy_mobile.online.model.DiaDaSemana;
import br.com.flashstudy.flashstudy_mobile.online.model.Horario;

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

    public boolean salvar(Ciclo ciclo) {

        long usuario_codigo = ciclo.getUsuario().getCodigo();

        CicloOff cicloOff = new CicloOff();
        cicloOff.setCodigo(ciclo.getCodigo());
        cicloOff.setNumMaterias(ciclo.getNumMaterias());
        cicloOff.setUsuarioCodigo(usuario_codigo);

        List<HorarioOff> horarioOffs = new ArrayList<>();

        for (DiaDaSemana diaDaSemana : ciclo.getDias()) {
            for (Horario horario : diaDaSemana.getHorarios()) {
                HorarioOff horarioOff = new HorarioOff();
                horarioOff.setCodigo(horario.getCodigo());
                horarioOff.setDia(diaDaSemana.getNome());
                horarioOff.setDisciplinaCodigo(horario.getDisciplina().getCodigo());
                horarioOff.setUsuarioCodigo(usuario_codigo);
                horarioOff.setTempo(horario.getTempo());

                horarioOffs.add(horarioOff);
            }
        }

        if (ciclo.getCodigo() != 0) {
            try {
                new Deletar().execute(cicloOff).get();
                boolean res = horarioRepositoryOff.deletarLista(usuario_codigo);

                if (!res)
                    return false;
            } catch (Exception e) {
                Log.e("ERRO DELETAR CICLO", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        try {
            if (horarioRepositoryOff.salvarLista(horarioOffs)) {
                return new Salvar().execute(cicloOff).get();
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
