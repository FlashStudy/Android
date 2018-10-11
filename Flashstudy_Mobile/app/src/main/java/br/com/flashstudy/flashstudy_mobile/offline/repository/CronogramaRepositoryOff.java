package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;

public class CronogramaRepositoryOff {
    public static boolean salvar(CronogramaOff cronogramaOff, Context context) {
        try {
            AppDatabase.getAppDatabase(context).cronogramaDao().insert(cronogramaOff);
            AppDatabase.getAppDatabase(context).disciplinaDao().insert(cronogramaOff.getDisciplinas());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static CronogramaOff buscarPorUsario(long codigo, Context context){
        try{
            return AppDatabase.getAppDatabase(context).cronogramaDao().getCronogramaByUsuario(codigo);
        }catch (Exception e){
            Log.i("ERRO NA CONSULTA", e.getMessage());
            return null;
        }
    }
}
