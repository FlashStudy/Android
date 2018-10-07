package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.FlashcardRepositoryOff;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class FlashcardCrudActivity extends AppCompatActivity {

    @BindViews({R.id.txtTitulo, R.id.txtPergunta, R.id.txtResposta})
    List<EditText> campos;

    private FlashcardRepositoryOff flashcardRepositoryOff = new FlashcardRepositoryOff();

    static final int SALVAR = Menu.FIRST;
    static final int DELETAR = Menu.FIRST + 1;
    static final int CANCELAR = Menu.FIRST + 2;
    static final int EDITAR = Menu.FIRST + 3;
    static final int RESPOSTA = Menu.FIRST + 4;

    FlashcardOff flashcardOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_crud);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Intent intent;

        try {
            intent = getIntent();
            flashcardOff = (FlashcardOff) intent.getSerializableExtra("flashcard");
            campos.get(0).setText(flashcardOff.getTitulo());
            campos.get(1).setText(flashcardOff.getPergunta());
            campos.get(2).setText("Clique no botão 'Ver resposta' !");

            campos.get(0).setEnabled(false);
            campos.get(1).setEnabled(false);
            campos.get(2).setEnabled(false);
        } catch (Exception e) {

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
                if (flashcardOff == null) {
                    flashcardOff = new FlashcardOff();
                    flashcardOff.setTitulo(campos.get(0).getText().toString());
                    flashcardOff.setPergunta(campos.get(1).getText().toString());
                    flashcardOff.setResposta(campos.get(2).getText().toString());

                    if (salvarFlashcard(flashcardOff)) {
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), "Flashcard salvo com sucesso", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    return false;
                }
            case DELETAR:
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("AVISO!");
                dlg.setMessage("Tem certeza em deletar esse flashcard?");

                dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            boolean result = flashcardRepositoryOff.deletar(flashcardOff, getApplicationContext());
                            if (result) {
                                Toast.makeText(getApplicationContext(), "Flashcard deletado com sucesso!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Houve um erro ao deletar o flashcard!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dlg.show();
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
                campos.get(2).setText(flashcardOff.getResposta());
                campos.get(0).setEnabled(true);
                campos.get(1).setEnabled(true);
                campos.get(2).setEnabled(true);
                return true;

            case RESPOSTA:
                campos.get(2).setText(flashcardOff.getResposta());
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private boolean salvarFlashcard(FlashcardOff flashcardOff) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
            long codigo = sharedPreferences.getLong("codigo", 0);

            flashcardOff.setUsuarioCodigo(codigo);

            return flashcardRepositoryOff.salvar(flashcardOff, getApplicationContext());
        } catch (Exception e) {
            Log.i("ERRO NA CONSULTA", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
