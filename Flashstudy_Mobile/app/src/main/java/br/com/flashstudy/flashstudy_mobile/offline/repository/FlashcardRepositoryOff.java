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
            Log.i("ERRO LISTAR FLASH", e.getMessage());
            return null;
        }
    }

    public static List<FlashcardOff> listarPorPasta(long codigo, Context context) {
        try {
            return AppDatabase.getAppDatabase(context).flashcardDao().getAllFlashcardOffsByPasta(codigo);
        } catch (Exception e) {
            Log.i("ERRO LISTAR FLASH", e.getMessage());
            return null;
        }
    }

    public static boolean salvar(FlashcardOff flashcardOff, Context context) {
        try {
            AppDatabase.getAppDatabase(context).flashcardDao().insert(flashcardOff);
            return true;
        } catch (Exception e) {
            Log.i("ERRO SALVAR FLASH", e.getMessage());
            return false;
        }
    }

    public static boolean deletar(FlashcardOff flashcardOff, Context context) {
        try {
            AppDatabase.getAppDatabase(context).flashcardDao().delete(flashcardOff);
            return true;
        } catch (Exception e) {
            Log.i("ERRO DELETAR FLASH", e.getMessage());
            return false;
        }
    }

    public static boolean atualizar(FlashcardOff flashcardOff, Context context) {
        try {
            AppDatabase.getAppDatabase(context).flashcardDao().update(flashcardOff);
            return true;
        } catch (Exception e) {
            Log.i("ERRO ATUALIZAR FLASH ", e.getMessage());
            return false;
        }
    }

}
