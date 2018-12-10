package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.Intent;
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
import br.com.flashstudy.flashstudy_mobile.adapter.CronogramaAdapter;
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

    private long codigo;

    private CronogramaRepositoryOff cronogramaRepositoryOff;
    private DisciplinaRepositoryOff disciplinaRepositoryOff;
    private CronogramaOff cronograma = new CronogramaOff();
    private List<DisciplinaOff> offs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cronogramaRepositoryOff = new CronogramaRepositoryOff(this);
        disciplinaRepositoryOff = new DisciplinaRepositoryOff(this);
        codigo = Util.getLocalUserCodigo(this);
        populaTela();
    }

    public void populaTela() {

        try {
            cronograma = cronogramaRepositoryOff.buscarPorUsario(codigo);
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
                cronograma.setDisciplinas(disciplinaRepositoryOff.listar(codigo));
                offs = cronograma.getDisciplinas();

                lblInicio.setText("Início: " + cronograma.getInicio());
                lblFim.setText("Fim: " + cronograma.getFim());
            } catch (Exception e) {
                Toast.makeText(CronogramaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

            CronogramaAdapter adapter = new CronogramaAdapter(this, this, offs);

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
        Intent intent = new Intent(CronogramaActivity.this, DisciplinaCrudActivity.class);
        intent.putExtra("disciplina", offs.get(position));
        startActivity(intent);

    }

}
