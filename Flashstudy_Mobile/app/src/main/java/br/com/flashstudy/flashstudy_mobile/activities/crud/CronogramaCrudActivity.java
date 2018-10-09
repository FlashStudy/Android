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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.ConversaoDeClasse;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.adapter.model.DisciplinaListModel;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;
import br.com.flashstudy.flashstudy_mobile.online.repository.CronogramaRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CronogramaCrudActivity extends AppCompatActivity {

    @BindView(R.id.txtInicio)
    EditText txtInicio;

    @BindView(R.id.txtFim)
    EditText txtFim;

    @BindView(R.id.txtDisciplina)
    EditText txtDisciplina;

    @BindView(R.id.swipeMenuDisciplinas)
    SwipeMenuListView swipeMenuListView;

    Cronograma cronograma;

    List<DisciplinaListModel> disciplinasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma_crud);


        ButterKnife.bind(this);

        Intent intent;

        try {
            intent = getIntent();
            cronograma = (Cronograma) intent.getSerializableExtra("cronograma");
        } catch (Exception e) {
            Toast.makeText(CronogramaCrudActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }

        if (cronograma != null) {
            for (Disciplina disciplina : cronograma.getDisciplinas()){
                disciplinasList.add(ConversaoDeClasse.disciplinaToDisciplinaList(disciplina));
            }
            txtFim.setText(cronograma.getFim());
            txtInicio.setText(cronograma.getInicio());

            populaLista();

        } else {
            cronograma = new Cronograma();
        }
    }

    public void populaLista() {
        ArrayAdapter adapter = new ArrayAdapter(CronogramaCrudActivity.this, android.R.layout.simple_list_item_1, disciplinasList);
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
                        AlertDialog.Builder dlg = new AlertDialog.Builder(CronogramaCrudActivity.this);
                        dlg.setTitle("AVISO!");
                        dlg.setMessage("Tem certeza em deletar essa disciplina?");

                        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (disciplinasList.get(position).getCodigo() != null) {

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
                                    disciplinasList.remove(position);
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
    public void salvarCronograma() {
        Usuario usuario;

        try {
            UsuarioOff usuarioOff = new GetLocalUser().execute().get();
            usuario = ConversaoDeClasse.usuarioOffToUsuario(usuarioOff);
        } catch (Exception e) {
            usuario = new Usuario();
            usuario.setCodigo(Util.getLocalUserCodigo(CronogramaCrudActivity.this));
        }

        cronograma.setUsuario(usuario);

        List<Disciplina> disciplinaSet = new ArrayList<>();
        for (DisciplinaListModel d : disciplinasList){
            d.setUsuario(usuario);
            disciplinaSet.add(ConversaoDeClasse.disciplinaListToDisciplina(d));
        }

        cronograma.setDisciplinas(disciplinaSet);
        cronograma.setInicio(txtInicio.getText().toString().trim());
        cronograma.setFim(txtFim.getText().toString().trim());

        try {
            if(new SalvarCronograma().execute(cronograma).get()){
                Toast.makeText(CronogramaCrudActivity.this, "Cronograma salvo com sucesso", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

        }


        Log.i("CRONOGRAMA", cronograma.toString());

    }

    @OnClick(R.id.btnAddMateria)
    public void addMateria() {

        if (!txtDisciplina.getText().toString().trim().equals("")) {

            DisciplinaListModel disciplina = new DisciplinaListModel();
            disciplina.setNome(txtDisciplina.getText().toString().trim());

            txtDisciplina.setText("");

            disciplinasList.add(disciplina);
            populaLista();
        } else {
            Toast.makeText(CronogramaCrudActivity.this, "O campo está vazio!", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    private class GetLocalUser extends AsyncTask<Void, Void, UsuarioOff> {

        @Override
        protected UsuarioOff doInBackground(Void... voids) {
            return Util.getLocalUser(CronogramaCrudActivity.this, Util.getLocalUserCodigo(CronogramaCrudActivity.this));
        }
    }

    private class SalvarCronograma extends AsyncTask<Cronograma, Void, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(CronogramaCrudActivity.this);
            progressDialog.setMessage("Isso pode levar algum tempo...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Cronograma... cronogramas) {
            try {
                CronogramaRepository cronogramaRepository = new CronogramaRepository();
                cronogramaRepository.salvar(cronogramas[0]);
                return true;
            } catch (Exception e) {
                Log.i("ERRO SALVAR CRONOGRAMA", e.getMessage());
                cancel(true);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
        }
    }

}
