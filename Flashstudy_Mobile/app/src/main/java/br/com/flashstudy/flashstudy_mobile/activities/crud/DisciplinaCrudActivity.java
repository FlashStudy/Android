package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.AssuntoRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.DisciplinaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisciplinaCrudActivity extends AppCompatActivity {

    @BindView(R.id.txtAssunto)
    EditText txtAssunto;

    @BindView(R.id.lblDisciplina)
    TextView textViewDisciplina;

    @BindView(R.id.swipeMenuAssuntos)
    SwipeMenuListView swipeMenuListView;

    private DisciplinaOff disciplinaOff;
    private List<AssuntoOff> assuntos = new ArrayList<>();

    long codigoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina_crud);

        ButterKnife.bind(this);

        codigoUsuario = Util.getLocalUserCodigo(DisciplinaCrudActivity.this);

        Intent intent;

        try {
            intent = getIntent();
            disciplinaOff = (DisciplinaOff) intent.getSerializableExtra("disciplina");
        } catch (Exception e) {
            Toast.makeText(DisciplinaCrudActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }


        if (disciplinaOff != null) {
            textViewDisciplina.setText(disciplinaOff.getNome());
            assuntos.addAll(disciplinaOff.getAssuntos());

            populaLista();

        } else {
            disciplinaOff = new DisciplinaOff();
        }

    }


    public void populaLista() {
        ArrayAdapter adapter = new ArrayAdapter(DisciplinaCrudActivity.this, android.R.layout.simple_list_item_1, assuntos);
        swipeMenuListView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                        0, 0)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        swipeMenuListView.setMenuCreator(creator);

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {

                    //deletar
                    case 0:
                        AlertDialog.Builder dlg = new AlertDialog.Builder(DisciplinaCrudActivity.this);
                        dlg.setTitle("AVISO!");
                        dlg.setMessage("Tem certeza em deletar esse assunto?");

                        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (assuntos.get(position).getCodigo() != 0) {

                                /*try {
                                    boolean result = flashcardRepositoryOff.deletar(flashcardOff, getApplicationContext());
                                    if (result) {
                                        Toast.makeText(getApplicationContext(), "Flashcard deletado com sucesso!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Houve um erro ao deletar o flashcard!", Toast.LENGTH_LONG).show();
                                }*/
                                } else {
                                    assuntos.remove(position);
                                    populaLista();
                                }
                            }
                        });
                        dlg.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @OnClick(R.id.fab)
    public void salvarAssuntos() {
        try {
            new SalvarAssuntos().execute(assuntos);
        } catch (Exception e) {
            Toast.makeText(DisciplinaCrudActivity.this, "Houve um erro ao salvar o cronograma! Tente novamente!", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btnAddAssunto)
    public void addMateria() {

        if (!Util.isCampoVazio(txtAssunto.getText().toString())) {

            AssuntoOff a = new AssuntoOff();
            a.setTema(txtAssunto.getText().toString().trim());
            a.setDisciplinaCodigo(disciplinaOff.getCodigo());
            a.setUsuarioCodigo(codigoUsuario);

            assuntos.add(a);

            txtAssunto.setText("");

            populaLista();
        } else {
            Toast.makeText(DisciplinaCrudActivity.this, "O campo está vazio!", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    private class SalvarAssuntos extends AsyncTask<List<AssuntoOff>, Void, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(DisciplinaCrudActivity.this);
            progressDialog.setMessage("Salvando disciplina... ");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(List<AssuntoOff>... assuntos) {
            try {
                AssuntoRepositoryOff.salvarAssuntos(assuntos[0], DisciplinaCrudActivity.this);
                return true;
            } catch (Exception e) {
                Toast.makeText(DisciplinaCrudActivity.this, "Houve um erro ao salvar o cronograma", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            finish();
        }
    }

    private class AtualizarAssuntos extends AsyncTask<List<AssuntoOff>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<AssuntoOff>... assuntos) {
            try {
                AssuntoRepositoryOff.salvarAssuntos(assuntos[0], DisciplinaCrudActivity.this);
                return true;
            } catch (Exception e) {
                Toast.makeText(DisciplinaCrudActivity.this, "Houve um erro ao salvar o cronograma", Toast.LENGTH_LONG).show();
                return false;
            }
        }

    }
}
