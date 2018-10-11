package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;

public class FlashcardRepositoryOff {

    public static List<FlashcardOff> listar(long codigo, Context context) {
        try {
            return AppDatabase.getAppDatabase(context).flashcardDao().getAllFlashcardsByUsuario(codigo);
        } catch (Exception e) {
            Log.i("ERRO CONSULT SERV", e.getMessage());
            return null;
        }
    }

    public static boolean salvar(FlashcardOff flashcardOff, Context context) {
        try {
            AppDatabase.getAppDatabase(context).flashcardDao().insert(flashcardOff);
            return true;
        } catch (Exception e) {
            Log.i("ERRO AO SALVAR", e.getMessage());
            return false;
        }
    }

    public static boolean deletar(FlashcardOff flashcardOff, Context context) {
        try {
            AppDatabase.getAppDatabase(context).flashcardDao().delete(flashcardOff);
            return true;
        } catch (Exception e) {
            Log.i("ERRO AO DELETAR", e.getMessage());
            return false;
        }
    }

}
