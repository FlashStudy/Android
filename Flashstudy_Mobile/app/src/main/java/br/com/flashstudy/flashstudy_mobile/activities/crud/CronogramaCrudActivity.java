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
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CronogramaRepositoryOff;
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

    CronogramaOff cronograma;
    List<DisciplinaOff> disciplinaOffs = new ArrayList<>();

    long codigoUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma_crud);

        ButterKnife.bind(this);

        codigoUsuario = Util.getLocalUserCodigo(CronogramaCrudActivity.this);

        Intent intent;

        try {
            intent = getIntent();
            cronograma = (CronogramaOff) intent.getSerializableExtra("cronograma");
        } catch (Exception e) {
            Toast.makeText(CronogramaCrudActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }


        if (cronograma != null) {
            disciplinaOffs.addAll(cronograma.getDisciplinas());
            txtFim.setText(cronograma.getFim());
            txtInicio.setText(cronograma.getInicio());

            populaLista();

        } else {
            cronograma = new CronogramaOff();
        }
    }

    public void populaLista() {
        ArrayAdapter adapter = new ArrayAdapter(CronogramaCrudActivity.this, android.R.layout.simple_list_item_1, disciplinaOffs);
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

                                if (disciplinaOffs.get(position).getCodigo() != 0) {

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
                                    disciplinaOffs.remove(position);
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

        if (isDataValida(txtInicio.getText().toString().trim()) && isDataValida(txtFim.getText().toString().trim())) {
            cronograma.setUsuarioCodigo(codigoUsuario);
            cronograma.setDisciplinas(disciplinaOffs);
            cronograma.setInicio(txtInicio.getText().toString().trim());
            cronograma.setFim(txtFim.getText().toString().trim());

            try {
                new SalvarCronograma().execute(cronograma);
            } catch (Exception e) {
                Toast.makeText(CronogramaCrudActivity.this, "Houve um erro ao salvar o cronograma!", Toast.LENGTH_LONG).show();
            }
        }


    }

    @OnClick(R.id.btnAddMateria)
    public void addMateria() {

        if (!Util.isCampoVazio(txtDisciplina.getText().toString())) {

            DisciplinaOff d = new DisciplinaOff();
            d.setNome(txtDisciplina.getText().toString().trim());
            d.setUsuarioCodigo(codigoUsuario);
            txtDisciplina.setText("");

            disciplinaOffs.add(d);
            populaLista();
        } else {
            Toast.makeText(CronogramaCrudActivity.this, "O campo está vazio!", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    public boolean isFormatoDaDataCerto(String data) {
        Date currentTime = Calendar.getInstance().getTime();

        String[] strings = data.split("/");
        if (strings.length < 3 || strings.length > 3) {
            return false;
        } else {
            if (Integer.parseInt(strings[0]) < 1 || Integer.parseInt(strings[0]) > 31) {
                return false;
            } else {
                if (Integer.parseInt(strings[1]) < 1 || Integer.parseInt(strings[1]) > 12) {
                    return false;
                } else {
                    if (Integer.parseInt(strings[2]) < currentTime.getYear()) {
                        return false;
                    } else {
                        return true;
                    }
                }

            }
        }
    }

    public boolean isDataValida(String data) {
        if (isFormatoDaDataCerto(data)) {
            Date currentTime = Calendar.getInstance().getTime();
            String[] strings = data.split("/");

            if (Integer.parseInt(strings[1]) < currentTime.getMonth()) {
                return false;
            }

            if (Integer.parseInt(strings[1]) == currentTime.getMonth()) {
                if (Integer.parseInt(strings[0]) < currentTime.getDay()) {
                    return false;
                } else return true;
            }

            if (Integer.parseInt(strings[1]) > currentTime.getMonth()) {
                return true;
            }

        } else {
            return false;
        }
        return false;
    }

    private class SalvarCronograma extends AsyncTask<CronogramaOff, Void, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(CronogramaCrudActivity.this);
            progressDialog.setMessage("Salvando cronograma... ");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(CronogramaOff... cronogramaOffs) {
            try {
                CronogramaRepositoryOff.salvar(cronogramaOffs[0], CronogramaCrudActivity.this);
                return true;
            } catch (Exception e) {
                Toast.makeText(CronogramaCrudActivity.this, "Houve um erro ao salvar o cronograma", Toast.LENGTH_LONG).show();
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
}
