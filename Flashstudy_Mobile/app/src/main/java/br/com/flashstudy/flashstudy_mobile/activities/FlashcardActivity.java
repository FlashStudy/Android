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

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.activities.crud.FlashcardCrudActivity;
import br.com.flashstudy.flashstudy_mobile.adapter.FlashcardDeleteAdapter;
import br.com.flashstudy.flashstudy_mobile.adapter.FlashcardListAdapter;
import br.com.flashstudy.flashstudy_mobile.adapter.PastaDeleteAdapter;
import br.com.flashstudy.flashstudy_mobile.adapter.PastaListAdapter;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.FlashcardRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.PastaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.repository.FlashcardRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnTextChanged;

public class FlashcardActivity extends AppCompatActivity {

    @BindView(R.id.listFlashcards)
    public ListView listViewFlashcards;

    @BindView(R.id.listPastas)
    public ListView listViewPastas;

    @BindView(R.id.txt_filtro)
    public EditText txt_filtro;

    static final int FLASHCARDS = Menu.FIRST;
    static final int PASTAS = Menu.FIRST + 1;

    static final int NOVO_FLASHCARD = Menu.FIRST + 2;
    static final int DELETAR_FLASHCARDS = Menu.FIRST + 3;

    static final int NOVA_PASTA = Menu.FIRST + 4;
    static final int DELETAR_PASTA = Menu.FIRST + 5;

    private FlashcardRepository flashcardRepository = new FlashcardRepository();
    private FlashcardRepositoryOff flashcardRepositoryOff;
    private PastaRepositoryOff pastaRepositoryOff;

    private List<FlashcardOff> flashcards;
    private List<PastaOff> pastas;

    private boolean pasta_selecionada;
    private long codigo;
    private long pasta_codigo;

    private boolean deletar_selecionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Flashcards");
        setSupportActionBar(toolbar);

        flashcardRepositoryOff = new FlashcardRepositoryOff(this);
        pastaRepositoryOff = new PastaRepositoryOff(this);
        pasta_selecionada = false;
        codigo = Util.getLocalUserCodigo(this);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            pastas = pastaRepositoryOff.listar(codigo);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro ao listar flashcards/pastas!", Toast.LENGTH_LONG).show();
            finish();
        }
        deletar_selecionados = false;
        populaTelaPastas();
    }


    private void filtrar() {

    }

    public void populaTelaFlashcards() {

        FlashcardListAdapter adapter = new FlashcardListAdapter(this, this, flashcards);
        listViewFlashcards.setAdapter(adapter);
        listViewFlashcards.setVisibility(View.VISIBLE);
        listViewPastas.setVisibility(View.GONE);

        if (flashcards.size() == 0) {
            Toast.makeText(getApplicationContext(), "Nenhum flashcard cadastrado!", Toast.LENGTH_SHORT).show();
        }
    }

    public void populaTelaPastas() {

        PastaListAdapter adapter = new PastaListAdapter(this, this, pastas);
        listViewPastas.setAdapter(adapter);

        listViewFlashcards.setVisibility(View.GONE);
        listViewPastas.setVisibility(View.VISIBLE);

        if (pastas.size() == 0)
            Toast.makeText(getApplicationContext(), "Nenhuma pasta cadastrada!", Toast.LENGTH_SHORT).show();

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

            case DELETAR_FLASHCARDS:
                if (deletar_selecionados) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("AVISO!");
                    dlg.setMessage("Tem certeza em deletar esses flashcards?");
                    dlg.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (flashcards != null) {
                                FlashcardDeleteAdapter adapter = (FlashcardDeleteAdapter) listViewFlashcards.getAdapter();

                                List<FlashcardOff> flashcardsParaExcluir = new ArrayList<>();

                                for (int i = 0; i < adapter.getCount(); i++) {
                                    if (adapter.isSelecionado(i)) {
                                        flashcardsParaExcluir.add(flashcards.get(i));
                                    }
                                }

                                if (flashcardRepositoryOff.deletarLista(flashcardsParaExcluir)) {
                                    flashcards = flashcardRepositoryOff.listarPorPasta(pasta_codigo);
                                    deletar_selecionados = false;
                                    populaTelaFlashcards();
                                }

                            }
                        }
                    });
                    dlg.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            flashcards = flashcardRepositoryOff.listarPorPasta(pasta_codigo);
                            deletar_selecionados = false;
                            populaTelaFlashcards();
                        }
                    });
                    dlg.show();
                } else {
                    FlashcardDeleteAdapter adapter = new FlashcardDeleteAdapter(this, this, flashcards);
                    deletar_selecionados = true;
                    listViewFlashcards.setAdapter(adapter);
                }

                break;
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

                                    pastas = pastaRepositoryOff.listar(codigo);

                                    populaTelaPastas();
                                } catch (Exception e) {
                                    Toast.makeText(FlashcardActivity.this, "Erro ao salvar a pasta!", Toast.LENGTH_SHORT).show();
                                    Log.e("ERRO SALVAR PASTA", e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });
                alertDialog.show();
                break;
            case DELETAR_PASTA:
                if (deletar_selecionados) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("AVISO!");
                    dlg.setMessage("Tem certeza em deletar essas pastas?");
                    dlg.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (pastas != null) {
                                PastaDeleteAdapter adapter = (PastaDeleteAdapter) listViewPastas.getAdapter();

                                List<PastaOff> pastasParaExcluir = new ArrayList<>();

                                for (int i = 0; i < adapter.getCount(); i++) {
                                    if (adapter.isSelecionado(i)) {
                                        pastasParaExcluir.add(pastas.get(i));
                                    }
                                }

                                if (pastaRepositoryOff.deletarLista(pastasParaExcluir)) {
                                    pastas = pastaRepositoryOff.listar(codigo);
                                    deletar_selecionados = false;
                                    populaTelaPastas();
                                }

                            }
                        }
                    });
                    dlg.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pastas = pastaRepositoryOff.listar(codigo);
                            deletar_selecionados = false;
                            populaTelaPastas();
                        }
                    });
                    dlg.show();
                } else {
                    PastaDeleteAdapter adapter = new PastaDeleteAdapter(this, this, pastas);
                    deletar_selecionados = true;
                    listViewPastas.setAdapter(adapter);
                }

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
        pasta_selecionada = true;
        pasta_codigo = pastaOff.getCodigo();
        try {
            flashcards = flashcardRepositoryOff.listarPorPasta(pastaOff.getCodigo());
            populaTelaFlashcards();
        } catch (Exception e) {
            Toast.makeText(this, "Houve um erro ao acessar os flashcards!", Toast.LENGTH_LONG).show();
            Log.e("ERRO LISTAR PASTA", e.getMessage());
            e.printStackTrace();
            finish();
        }

    }

    @OnItemLongClick(R.id.listPastas)
    public boolean editarPasta(int position) {

        final PastaOff pastaOff = pastas.get(position);

        if (position > 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Editar pasta");
            alertDialog.setMessage("Nome da pasta:");

            final EditText input = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.setText(pastaOff.getNome());
            alertDialog.setView(input);
            alertDialog.setIcon(R.drawable.ic_edit);

            alertDialog.setPositiveButton("Salvar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            pastaOff.setNome(input.getText().toString().trim());

                            if (pastaRepositoryOff.atualizar(pastaOff)) {
                                pastas = pastaRepositoryOff.listar(codigo);
                                populaTelaPastas();
                            }
                        }
                    });
            alertDialog.setNegativeButton("Cancelar", null);
            alertDialog.show();
        } else {
            Toast.makeText(this, "Essa pasta não pode ser alterada!", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @OnTextChanged(R.id.txt_filtro)
    public void filtro() {
        String filtro = txt_filtro.getText().toString().trim();

        if (filtro.equals("")) {
            if (pasta_selecionada) {
                flashcards = flashcardRepositoryOff.listarPorPasta(pasta_codigo);
                populaTelaFlashcards();
            } else {
                pastas = pastaRepositoryOff.listar(codigo);
                populaTelaPastas();
            }
        } else {
            if (pasta_selecionada) {
                flashcards = flashcardRepositoryOff.listarPorPasta(pasta_codigo, filtro);
                populaTelaFlashcards();
            } else {
                pastas = pastaRepositoryOff.pesquisar(codigo, filtro);
                populaTelaPastas();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (pasta_selecionada) {
            populaTelaPastas();
            pasta_selecionada = false;
        } else {
            finish();
        }
    }
}
