package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;

public class HorarioRepositoryOff {
    public static List<HorarioOff> listarPorUsuario(long codigo, Context context){
        try{
            return AppDatabase.getAppDatabase(context).horarioDao().getAllHorariosByUsuario(codigo);
        }catch (Exception e){
            Log.e("ERRO REPOSITORY", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
