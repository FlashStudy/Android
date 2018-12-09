package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.online.clients.FlashcardRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Flashcard;

public class FlashcardRepository {
    private FlashcardRestClient flashcardRestClient = new FlashcardRestClient();

    public List<Flashcard> listarFlashcards(long codigo) {
        try {
            return new ListaFlashcardsAsync().execute(codigo).get();
        } catch (Exception e) {
            Log.i("ERRO CONSULT SERV", e.getMessage());
            return null;
        }
    }

    public Flashcard salvar(Flashcard flashcard) {
        try {
            return new SalvaFlashcardAsync().execute(flashcard).get();
        } catch (Exception e) {
            Log.i("ERRO AO SALVAR", e.getMessage());
            return null;
        }
    }

    public boolean deletar(Flashcard flashcard) {
        try {
            return new DeletarFlashcardAsync().execute(flashcard).get();
        } catch (Exception e) {
            Log.i("ERRO AO DELETAR", e.getMessage());
            return false;
        }
    }

    private class ListaFlashcardsAsync extends AsyncTask<Long, Void, List<Flashcard>> {

        @Override
        protected List<Flashcard> doInBackground(Long... longs) {
            try{
                return flashcardRestClient.findAll(longs[0]);
            }catch (Exception e){
                Log.e("ERRO ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

    }

    private class SalvaFlashcardAsync extends AsyncTask<Flashcard, Void, Flashcard> {

        @Override
        protected Flashcard doInBackground(Flashcard... flashcards) {
            return null;
        }

    }

    private class DeletarFlashcardAsync extends AsyncTask<Flashcard, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Flashcard... flashcards) {
            return null;
        }

    }
}
