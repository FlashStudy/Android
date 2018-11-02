package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Horario;

public class CicloRepositoryOff {

    public static CicloOff buscarPorUsario(long codigo, Context context) {
        try {
            return AppDatabase.getAppDatabase(context).cicloDao().getCicloByUsuario(codigo);
        } catch (Exception e) {
            Log.i("ERRO BUSCAR CICLO", e.getMessage());
            return null;
        }
    }

    public static boolean salvar(CicloOff cicloOff, List<String> dias, Context context) {

        if(cicloOff.getCodigo() != 0){
            try{
                AppDatabase.getAppDatabase(context).cicloDao().delete(cicloOff);
            }catch (Exception e){
                Log.e("ERRO DELETAR CICLO ATT", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        try {
            Random rand = new Random();

            CicloOff c = new CicloOff(cicloOff.getNumMaterias(), Util.getLocalUserCodigo(context));

            List<DisciplinaOff> disciplinas = DisciplinaRepositoryOff.listarDisciplinas(Util.getLocalUserCodigo(context), context);

            int arrLength = disciplinas.size();

            List<HorarioOff> horarios = new ArrayList<>();

            if (cicloOff.getHorarios() != null) {
                AppDatabase.getAppDatabase(context).horarioDao().deleteLista(cicloOff.getHorarios());
            }

            for (int i = 0; i < dias.size(); i++) {
                for (int j = 0; j < cicloOff.getNumMaterias(); j++) {
                    HorarioOff horarioOff = new HorarioOff(j+1, disciplinas.get(rand.nextInt(arrLength)).getCodigo(), Util.getLocalUserCodigo(context), dias.get(i));
                    horarios.add(horarioOff);
                }
            }

            Log.i("HORARIOS", horarios.toString());

            AppDatabase.getAppDatabase(context).horarioDao().insertLista(horarios);
            AppDatabase.getAppDatabase(context).cicloDao().insert(c);

            return true;
        } catch (Exception e) {
            Log.e("ERRO SALVAR CICLO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
