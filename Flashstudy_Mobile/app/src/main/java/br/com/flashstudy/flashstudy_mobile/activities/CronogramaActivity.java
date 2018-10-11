package br.com.flashstudy.flashstudy_mobile.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.activities.crud.CronogramaCrudActivity;
import br.com.flashstudy.flashstudy_mobile.activities.crud.DisciplinaCrudActivity;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CronogramaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.DisciplinaRepositoryOff;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class CronogramaActivity extends AppCompatActivity {

    @BindView(R.id.swipeMenuDisciplinas)
    SwipeMenuListView swipeMenuListView;

    private CronogramaOff cronograma = new CronogramaOff();
    List<DisciplinaOff> offs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populaTela();
    }

    public void populaTela() {

        try {
            cronograma = new BuscarCronograma().execute().get();
        } catch (Exception e) {
            android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(CronogramaActivity.this);
            dlg.setTitle("AVISO!");
            dlg.setMessage("Houve um erro durante a busca do cronograma! \n\n" + e.getMessage());

            dlg.setNeutralButton("OK", null);
            dlg.show();

            finish();
        }

        if (cronograma != null) {
            try {
                cronograma.setDisciplinas(new BuscarDisciplinas().execute(cronograma.getCodigo()).get());
                offs = cronograma.getDisciplinas();

            } catch (Exception e) {
                Toast.makeText(CronogramaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
            ArrayAdapter adapter = new ArrayAdapter(CronogramaActivity.this, android.R.layout.simple_list_item_1, offs);
            swipeMenuListView.setAdapter(adapter);

            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "edit" item
                    SwipeMenuItem editItem = new SwipeMenuItem(
                            getApplicationContext());
                    // set item background
                    editItem.setBackground(new ColorDrawable(Color.rgb(13,
                            79, 99)));
                    // set item width
                    editItem.setWidth(170);
                    // set a icon
                    editItem.setIcon(R.drawable.ic_edit);
                    // add to menu
                    menu.addMenuItem(editItem);

                }
            };

            // set creator
            swipeMenuListView.setMenuCreator(creator);

            swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                    switch (index) {

                        //editar
                        case 0:
                            Intent intent = new Intent(CronogramaActivity.this, DisciplinaCrudActivity.class);
                            intent.putExtra("disciplina", offs.get(position));
                            startActivity(intent);
                            break;
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Nenhum cronograma cadastrado!", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.fab)
    public void chamarTelaFlashcardCrud() {
        Intent intent = new Intent(CronogramaActivity.this, CronogramaCrudActivity.class);
        intent.putExtra("cronograma", cronograma);
        startActivity(intent);

    }

    private class BuscarCronograma extends AsyncTask<Void, Void, CronogramaOff> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(CronogramaActivity.this);
            progressDialog.setMessage("Buscando cronograma mais recente...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected CronogramaOff doInBackground(Void... voids) {
            long codigo = Util.getLocalUserCodigo(CronogramaActivity.this);

            try {
                return CronogramaRepositoryOff.buscarPorUsario(codigo, CronogramaActivity.this);
            } catch (Exception e) {
                Toast.makeText(CronogramaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(CronogramaOff cronograma) {
            progressDialog.dismiss();
        }
    }

    private class BuscarDisciplinas extends AsyncTask<Long, Void, List<DisciplinaOff>> {

        @Override
        protected List<DisciplinaOff> doInBackground(Long... longs) {
            try {
                return DisciplinaRepositoryOff.listarDisciplinas(longs[0], CronogramaActivity.this);
            } catch (Exception e) {
                Toast.makeText(CronogramaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }
}
