package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.AssuntoRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.DisciplinaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.FlashcardRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.PastaRepositoryOff;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class FlashcardCrudActivity extends AppCompatActivity {

    @BindViews({R.id.txtTitulo, R.id.txtPergunta, R.id.txtResposta})
    public List<EditText> campos;

    @BindView(R.id.spinAssuntos)
    public Spinner spinAssuntos;

    @BindView(R.id.spinDisciplinas)
    public Spinner spinDisciplinas;

    @BindView(R.id.spinPastas)
    public Spinner spinPastas;

    static final int SALVAR = Menu.FIRST;
    static final int DELETAR = Menu.FIRST + 1;
    static final int CANCELAR = Menu.FIRST + 2;
    static final int EDITAR = Menu.FIRST + 3;
    static final int RESPOSTA = Menu.FIRST + 4;

    private FlashcardRepositoryOff flashcardRepositoryOff;
    private DisciplinaRepositoryOff disciplinaRepositoryOff;
    private AssuntoRepositoryOff assuntoRepositoryOff;
    private PastaRepositoryOff pastaRepositoryOff;

    private FlashcardOff flashcardOff;

    private List<DisciplinaOff> disciplinas = new ArrayList<>();
    private List<AssuntoOff> assuntos = new ArrayList<>();
    private List<PastaOff> pastas = new ArrayList<>();

    private long codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_crud);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Flashcard");
        setSupportActionBar(toolbar);
        codigo = Util.getLocalUserCodigo(this);

        flashcardRepositoryOff = new FlashcardRepositoryOff(this);
        disciplinaRepositoryOff = new DisciplinaRepositoryOff(this);
        assuntoRepositoryOff = new AssuntoRepositoryOff(this);
        pastaRepositoryOff = new PastaRepositoryOff(this);

        ButterKnife.bind(this);

        Intent intent;

        try {
            intent = getIntent();
            flashcardOff = (FlashcardOff) intent.getSerializableExtra("flashcard");

            disciplinas = disciplinaRepositoryOff.listar(codigo);
            pastas = pastaRepositoryOff.listar(codigo);


            Log.e("DISCIPLINAS:", disciplinas.toString());

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, disciplinas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinDisciplinas.setAdapter(adapter);

            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pastas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinPastas.setAdapter(adapter);

            if (flashcardOff != null) {
                campos.get(0).setText(flashcardOff.getTitulo());
                campos.get(1).setText(flashcardOff.getPergunta());
                campos.get(2).setText("Clique no botão 'Ver resposta' !");

                campos.get(0).setEnabled(false);
                campos.get(1).setEnabled(false);
                campos.get(2).setEnabled(false);

                Log.e("FLASHCARD", flashcardOff.toString());

                int discPosition = 0;
                for (int i = 0; i < disciplinas.size(); i++) {
                    Log.e("DISCIPLINA", "pos: " + i + ": " + disciplinas.get(i).toString());
                    if (disciplinas.get(i).getCodigo() == flashcardOff.getDisciplinaCodigo()) {

                        discPosition = i;
                    }
                }

                Log.e("DISCPOSITION", "posicao = " + discPosition);

                spinDisciplinas.setSelection(discPosition);
            }


        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        menu.add(0, SALVAR, 0, "Salvar");

        if (flashcardOff != null) {
            menu.add(0, DELETAR, 0, "Deletar");
            menu.add(0, CANCELAR, 0, "Cancelar");
            menu.add(0, EDITAR, 0, "Editar");
            menu.add(0, RESPOSTA, 0, "Ver resposta");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case SALVAR:
                if (isConectado()) {
                    if (flashcardOff == null) {
                        flashcardOff = new FlashcardOff();
                        flashcardOff.setTitulo(campos.get(0).getText().toString());
                        flashcardOff.setPergunta(campos.get(1).getText().toString());
                        flashcardOff.setResposta(campos.get(2).getText().toString());

                        int discPosition = spinDisciplinas.getSelectedItemPosition();
                        int assuntoPosition = spinAssuntos.getSelectedItemPosition();
                        int pastaPosition = spinPastas.getSelectedItemPosition();

                        try {
                            if (disciplinas.get(discPosition).getCodigo() != 0 && assuntos.get(assuntoPosition).getCodigo() != 0) {

                                flashcardOff.setDisciplinaCodigo(disciplinas.get(discPosition).getCodigo());
                                flashcardOff.setAssuntoCodigo(assuntos.get(assuntoPosition).getCodigo());

                                if (pastas.size() > 0) {
                                    flashcardOff.setPastaCodigo(pastas.get(pastaPosition).getCodigo());
                                }else{
                                    flashcardOff.setPastaCodigo(0);
                                }
                                flashcardOff.setUsuarioCodigo(codigo);
                                Log.e("FLASHCARD", flashcardOff.toString());

                                try {
                                    flashcardRepositoryOff.salvar(flashcardOff);
                                    Toast.makeText(getApplicationContext(), "Flashcard salvo!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Houve um erro ao salvar o flashcard!", Toast.LENGTH_LONG).show();
                                }
                                return true;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            Log.e("ERRO" , e.getMessage());
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Selecione uma Disciplina e um Assunto válidos!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        flashcardOff.setTitulo(campos.get(0).getText().toString());
                        flashcardOff.setPergunta(campos.get(1).getText().toString());
                        flashcardOff.setResposta(campos.get(2).getText().toString());

                        try {
                            flashcardRepositoryOff.atualizar(flashcardOff);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Houve um erro ao salvar o flashcard!", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                } else {
                    AlertDialog.Builder dlg1 = new AlertDialog.Builder(this);
                    dlg1.setTitle("AVISO!");
                    dlg1.setMessage("Só é possível salvar quando há uma conexão com a internet!");
                    dlg1.setNeutralButton("Ok", null);
                    dlg1.show();
                }
                break;
            case DELETAR:
                if (isConectado()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("AVISO!");
                    dlg.setMessage("Tem certeza em deletar esse flashcard?");

                    dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                flashcardRepositoryOff.deletar(flashcardOff);
                            } catch (Exception e) {
                                Log.i("ERRO DELETAR FLASH", e.getMessage());
                                Toast.makeText(getApplicationContext(), "Houve um erro ao deletar o flashcard!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dlg.show();
                } else {
                    AlertDialog.Builder dlg1 = new AlertDialog.Builder(this);
                    dlg1.setTitle("AVISO!");
                    dlg1.setMessage("Só é possível deletar quando há uma conexão com a internet!");
                    dlg1.setNeutralButton("Ok", null);
                    dlg1.show();
                }


                return true;

            case CANCELAR:
                campos.get(0).setText(flashcardOff.getTitulo());
                campos.get(1).setText(flashcardOff.getPergunta());
                campos.get(2).setText("Clique no botão 'Ver resposta' !");

                campos.get(0).setEnabled(false);
                campos.get(1).setEnabled(false);
                campos.get(2).setEnabled(false);
                return true;

            case EDITAR:
                if (isConectado()) {
                    campos.get(2).setText(flashcardOff.getResposta());
                    campos.get(0).setEnabled(true);
                    campos.get(1).setEnabled(true);
                    campos.get(2).setEnabled(true);
                } else {
                    AlertDialog.Builder dlg1 = new AlertDialog.Builder(this);
                    dlg1.setTitle("AVISO!");
                    dlg1.setMessage("Só é possível editar quando há uma conexão com a internet!");
                    dlg1.setNeutralButton("Ok", null);
                    dlg1.show();
                }
                return true;

            case RESPOSTA:
                campos.get(2).setText(flashcardOff.getResposta());
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @OnItemSelected(R.id.spinDisciplinas)
    public void populaSpinAssuntos(int position) {
        try {
            assuntos = assuntoRepositoryOff.listarPorDisciplinaCodigo(disciplinas.get(position).getCodigo());

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, assuntos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinAssuntos.setAdapter(adapter);

            if (flashcardOff.getCodigo() != 0) {
                Log.e("ASSUNTOS:", assuntos.toString());

                int assuntoPosition = 0;
                for (int i = 0; i < assuntos.size(); i++) {
                    Log.e("ASSUNTO", "pos: " + i + ": " + assuntos.get(i).toString());
                    if (assuntos.get(i).getCodigo() == flashcardOff.getAssuntoCodigo()) {

                        assuntoPosition = i;
                    }
                }
                Log.e("ASSUNTOPOSITION", "posicao = " + assuntoPosition);

                spinAssuntos.setSelection(assuntoPosition);
            }
        } catch (Exception e) {

        }
    }

    private boolean isConectado() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
