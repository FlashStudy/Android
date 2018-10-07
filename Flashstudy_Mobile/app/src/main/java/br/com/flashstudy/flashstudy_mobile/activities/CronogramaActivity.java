package br.com.flashstudy.flashstudy_mobile.activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.activities.crud.CronogramaCrudActivity;
import br.com.flashstudy.flashstudy_mobile.activities.crud.DisciplinaCrudActivity;
import br.com.flashstudy.flashstudy_mobile.adapter.CronogramaListAdapter;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import br.com.flashstudy.flashstudy_mobile.online.repository.CronogramaRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class CronogramaActivity extends AppCompatActivity {

    @BindView(R.id.dynamic_list)
    public ListView listViewDisciplinas;

    private Cronograma cronograma = new Cronograma();
    private CronogramaRepository cronogramaRepository = new CronogramaRepository();

    List<Disciplina> disciplinas = new ArrayList<>();


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
            for (Disciplina d : cronograma.getDisciplinas()) {
                disciplinas.add(d);
            }

            listViewDisciplinas.setAdapter(new CronogramaListAdapter(CronogramaActivity.this, disciplinas));
        } else {
            Toast.makeText(getApplicationContext(), "Nenhum cronograma cadastrado!", Toast.LENGTH_LONG).show();
        }
    }

    @OnItemClick(R.id.dynamic_list)
    public void seleciona(int position) {
        Intent intent = new Intent(CronogramaActivity.this, DisciplinaCrudActivity.class);
        intent.putExtra("disciplina", disciplinas.get(position));
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void chamarTelaFlashcardCrud() {
        Intent intent = new Intent(CronogramaActivity.this, CronogramaCrudActivity.class);
        intent.putExtra("cronograma", cronograma);
        startActivity(intent);

    }

    private class BuscarCronograma extends AsyncTask<Void, Void, Cronograma> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(CronogramaActivity.this);
            progressDialog.setMessage("Buscando cronograma mais recente...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Cronograma doInBackground(Void... voids) {
            long codigo = Util.getLocalUserCodigo(CronogramaActivity.this);

            try {
                return cronogramaRepository.buscar(codigo);
            } catch (Exception e) {
                Toast.makeText(CronogramaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cronograma cronograma) {
            progressDialog.dismiss();
        }
    }
}
