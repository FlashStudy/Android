package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;

public class PastaRepositoryOff {
    public static List<PastaOff> listar(long codigo, Context context) {
        try {
            return AppDatabase.getAppDatabase(context).pastaDao().getAllPastasByUsuario(codigo);
        } catch (Exception e) {
            Log.e("ERRO LISTAR PASTAS", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static boolean salvar(PastaOff pastaOff, Context context) {
        try {
            AppDatabase.getAppDatabase(context).pastaDao().insert(pastaOff);
            return true;
        } catch (Exception e) {
            Log.e("ERRO SALVAR PASTA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
