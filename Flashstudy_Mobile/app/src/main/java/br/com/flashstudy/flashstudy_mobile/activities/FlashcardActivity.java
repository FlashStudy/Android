package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.activities.crud.FlashcardCrudActivity;
import br.com.flashstudy.flashstudy_mobile.adapter.FlashcardListAdapter;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.FlashcardRepositoryOff;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class FlashcardActivity extends AppCompatActivity {

    @BindView(R.id.dynamic_list)
    public ListView listViewFlashcards;

    static final int FLASHCARDS = Menu.FIRST;
    static final int PASTAS = Menu.FIRST + 1;

    static final int NOVO_FLASHCARD = Menu.FIRST + 2;
    static final int DELETAR_FLASHCARDS = Menu.FIRST + 3;

    static final int NOVA_PASTA = Menu.FIRST + 4;
    static final int DELETAR_PASTA = Menu.FIRST + 5;

    List<FlashcardOff> flashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populaTela();
    }

    public void populaTela(){
        try {
            flashcards = new ListarFlashcard().execute().get();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao listar flashcards!", Toast.LENGTH_LONG).show();
            finish();
        }
        
        if (flashcards != null) {
            listViewFlashcards.setAdapter(new FlashcardListAdapter(getApplicationContext(), flashcards));
        } else {
            Toast.makeText(getApplicationContext(), "Nenhum flashcard cadastrado!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SubMenu subMenu = menu.addSubMenu(0, FLASHCARDS, 0, "Flashcards");

        subMenu.add(0, NOVO_FLASHCARD, 0, "Novo Flashcard");
        subMenu.add(0, DELETAR_FLASHCARDS, 0, "Deletar Flashcards");

        subMenu = menu.addSubMenu(0, PASTAS, 0, "Pastas");
        subMenu.add(0, NOVA_PASTA, 0, "Nova Pasta");
        subMenu.add(0, DELETAR_PASTA, 0, "Deletar Pastas");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case NOVO_FLASHCARD:
                Intent intent = new Intent(FlashcardActivity.this, FlashcardCrudActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.fab)
    public void chamarTelaFlashcardCrud() {
        Intent intent = new Intent(FlashcardActivity.this, FlashcardCrudActivity.class);
        startActivity(intent);
    }

    @OnItemClick(R.id.dynamic_list)
    public void seleciona(int position){
        Intent intent = new Intent(FlashcardActivity.this, FlashcardCrudActivity.class);
        intent.putExtra("flashcard", flashcards.get(position));
        startActivity(intent);
    }

    private class ListarFlashcard extends AsyncTask<Void, Void, List<FlashcardOff>>{

        @Override
        protected List<FlashcardOff> doInBackground(Void... voids) {
            try{
                long codigo = Util.getLocalUserCodigo(FlashcardActivity.this);
                return FlashcardRepositoryOff.listar(codigo, FlashcardActivity.this);
            }catch (Exception e){
                Log.i("ERRO LISTAR FLASH", e.getMessage());
                return null;
            }
        }
    }
}
