package br.com.flashstudy.flashstudy_mobile.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindView(R.id.list)
    ListView listView;

    @BindView(R.id.lblInicio)
    TextView lblInicio;

    @BindView(R.id.lblFim)
    TextView lblFim;

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

                lblInicio.setText("Início: " + cronograma.getInicio());
                lblFim.setText("Fim: " + cronograma.getFim());
            } catch (Exception e) {
                Toast.makeText(CronogramaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

            ArrayAdapter adapter = new ArrayAdapter(CronogramaActivity.this, android.R.layout.simple_list_item_1, offs);

            listView.setAdapter(adapter);


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

    @OnItemClick(R.id.list)
    public void editDisciplina(int position) {
        DisciplinaOff d = offs.get(position);

        Intent intent = new Intent(CronogramaActivity.this, DisciplinaCrudActivity.class);
        intent.putExtra("disciplina", offs.get(position));
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
