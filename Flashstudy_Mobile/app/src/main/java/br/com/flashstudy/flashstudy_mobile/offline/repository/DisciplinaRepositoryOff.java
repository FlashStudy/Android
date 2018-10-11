package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;

public class DisciplinaRepositoryOff {
    public static List<DisciplinaOff> listarDisciplinas(long codigo, Context context){
        try{
            return AppDatabase.getAppDatabase(context).disciplinaDao().getDisciplinaOffsByUsuario(codigo);
        }catch (Exception e){
            Log.i("ERRO NA CONSULTA", e.getMessage());
            return null;
        }
    }
}