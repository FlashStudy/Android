package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.activities.crud.FlashcardCrudActivity;
import br.com.flashstudy.flashstudy_mobile.adapter.FlashcardListAdapter;
import br.com.flashstudy.flashstudy_mobile.adapter.PastaListAdapter;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.FlashcardRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.PastaRepositoryOff;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class FlashcardActivity extends AppCompatActivity {

    @BindView(R.id.listFlashcards)
    public ListView listViewFlashcards;

    @BindView(R.id.listPastas)
    public ListView listViewPastas;

    static final int FLASHCARDS = Menu.FIRST;
    static final int PASTAS = Menu.FIRST + 1;

    static final int NOVO_FLASHCARD = Menu.FIRST + 2;
    static final int DELETAR_FLASHCARDS = Menu.FIRST + 3;

    static final int NOVA_PASTA = Menu.FIRST + 4;
    static final int DELETAR_PASTA = Menu.FIRST + 5;

    private FlashcardRepositoryOff flashcardRepositoryOff;
    private PastaRepositoryOff pastaRepositoryOff;

    private List<FlashcardOff> flashcards;
    private List<PastaOff> pastas;

    private boolean pasta_selecionada;
    private long codigo;

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
        flashcardRepositoryOff = new FlashcardRepositoryOff(this);
        pastaRepositoryOff = new PastaRepositoryOff(this);
        pasta_selecionada = false;
        codigo = Util.getLocalUserCodigo(this);
        populaTela();
    }

    public void populaTela() {
        pasta_selecionada = false;
        try {
            flashcards = flashcardRepositoryOff.listar(codigo);
            pastas = pastaRepositoryOff.listar(codigo);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro ao listar flashcards/pastas!", Toast.LENGTH_LONG).show();
            finish();
        }

        if (flashcards != null) {
            FlashcardListAdapter adapter = new FlashcardListAdapter(this, this, flashcards);
            listViewFlashcards.setAdapter(adapter);
            listViewPastas.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getApplicationContext(), "Nenhum flashcard cadastrado!", Toast.LENGTH_LONG).show();
        }

        if (pastas != null) {
            PastaListAdapter adapter = new PastaListAdapter(this, this, pastas);
            listViewPastas.setAdapter(adapter);

        }
    }

    public void populaTelaPasta() {
        pasta_selecionada = true;
        if (flashcards != null) {
            FlashcardListAdapter adapter = new FlashcardListAdapter(this, this, flashcards);
            listViewFlashcards.setAdapter(adapter);

            listViewPastas.setVisibility(View.GONE);
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
            case NOVA_PASTA:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FlashcardActivity.this);
                alertDialog.setTitle("Nova pasta");
                alertDialog.setMessage("Nome da pasta:");
                final EditText input = new EditText(FlashcardActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_edit);

                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                PastaOff pastaOff = new PastaOff();
                                pastaOff.setNome(input.getText().toString().trim());
                                pastaOff.setUsuarioCodigo(codigo);
                                try {
                                    pastaRepositoryOff.salvar(pastaOff);
                                    Toast.makeText(FlashcardActivity.this, "Pasta salva com sucesso!", Toast.LENGTH_SHORT).show();
                                    populaTela();
                                } catch (Exception e) {
                                    Toast.makeText(FlashcardActivity.this, "Erro ao salvar a pasta!", Toast.LENGTH_SHORT).show();
                                    Log.e("ERRO SALVAR PASTA", e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });
                alertDialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.fab)
    public void chamarTelaFlashcardCrud() {
        Intent intent = new Intent(FlashcardActivity.this, FlashcardCrudActivity.class);
        startActivity(intent);
    }

    @OnItemClick(R.id.listFlashcards)
    public void selecionaFlashcard(int position) {
        Intent intent = new Intent(FlashcardActivity.this, FlashcardCrudActivity.class);
        intent.putExtra("flashcard", flashcards.get(position));
        startActivity(intent);
    }

    @OnItemClick(R.id.listPastas)
    public void selecionaPasta(int position) {
        PastaOff pastaOff = pastas.get(position);
        try {
            flashcards = flashcardRepositoryOff.listarPorPasta(pastaOff.getCodigo());
            populaTelaPasta();
        } catch (Exception e) {
            Toast.makeText(this, "Houve um erro ao acessar os flashcards!", Toast.LENGTH_LONG).show();
            Log.e("ERRO LISTAR PASTA", e.getMessage());
            e.printStackTrace();
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (pasta_selecionada) {
            populaTela();
        } else {
            finish();
        }
    }
}
