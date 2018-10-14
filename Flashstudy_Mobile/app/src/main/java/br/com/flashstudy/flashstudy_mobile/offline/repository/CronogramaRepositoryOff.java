package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
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
            Log.i("ERRO SALVAR CRONOGRAMA", e.getMessage());
            return false;
        }
    }

    public static CronogramaOff buscarPorUsario(long codigo, Context context){
        try{
            return AppDatabase.getAppDatabase(context).cronogramaDao().getCronogramaByUsuario(codigo);
        }catch (Exception e){
            Log.i("ERRO BUSCAR CRONOGRAMA", e.getMessage());
            return null;
        }
    }

    public static boolean atualizar(CronogramaOff cronogramaOff, Context context){
        try {
            List<DisciplinaOff> novasDisciplinas = new ArrayList<>();
            for(DisciplinaOff d : cronogramaOff.getDisciplinas()){
                if(d.getCodigo()==0){
                    novasDisciplinas.add(d);
                }
            }

            AppDatabase.getAppDatabase(context).cronogramaDao().update(cronogramaOff);
            AppDatabase.getAppDatabase(context).disciplinaDao().insert(novasDisciplinas);
            return true;
        } catch (Exception e) {
            Log.i("ERRO UPDATE CRONOGRAMA", e.getMessage());
            return false;
        }
    }
}
