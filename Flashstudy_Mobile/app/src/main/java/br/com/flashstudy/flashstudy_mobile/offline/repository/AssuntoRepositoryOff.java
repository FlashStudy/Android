package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;

public class AssuntoRepositoryOff {

    public static boolean salvarAssuntos(List<AssuntoOff> assuntos, Context context){
        try{
            AppDatabase.getAppDatabase(context).assuntoDao().insertLista(assuntos);
            return true;
        }catch (Exception e){
            Log.i("ERRO SALVAR ASSUNTOS", e.getMessage());
            return false;
        }
    }

    public static List<AssuntoOff> listar(long codigo, Context context){
        try{
            return AppDatabase.getAppDatabase(context).assuntoDao().getAllAssuntosByDisciplina(codigo);
        }catch (Exception e){
            Log.i("ERRO BUSCAR ASSUNTOS", e.getMessage());
            return null;
        }
    }
}
