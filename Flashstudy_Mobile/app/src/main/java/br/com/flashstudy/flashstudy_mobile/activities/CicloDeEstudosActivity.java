package br.com.flashstudy.flashstudy_mobile.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.activities.crud.CicloDeEstudosCrudActivity;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CicloRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.DisciplinaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.HorarioRepositoryOff;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CicloDeEstudosActivity extends AppCompatActivity {

    CicloOff cicloOff;

    @BindView(R.id.tbl_layout)
    TableLayout tableLayout;

    private List<Integer> posDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_de_estudos);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populaTela();
    }

    @SuppressLint("NewApi")
    private void populaTela() {
        try {
            cicloOff = new BuscarCiclo().execute().get();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Houve um erro ao buscar o ciclo!", Toast.LENGTH_LONG).show();
            finish();
        }

        if (cicloOff == null) {
            Toast.makeText(getApplicationContext(), "Nenhum ciclo cadastrado!", Toast.LENGTH_LONG).show();
            cicloOff = new CicloOff();
        } else {

            List<DisciplinaOff> disciplinas = new ArrayList<>();

            try {
                disciplinas = new BuscarDisciplinas().execute().get();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Houve um erro ao buscar o ciclo!", Toast.LENGTH_LONG).show();
                Log.e("ERRO DISCIPLINAS", e.getMessage());
                e.printStackTrace();
                finish();
            }

            List<HorarioOff> horarioOffs = cicloOff.getHorarios();

            List<String> dias = ordenarDias(horarioOffs);

            // Adicionando dias
            TableRow titulo = new TableRow(this);
            titulo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (String dia : dias) {
                TextView txtDia = new TextView(this);
                txtDia.setText(dia);
                txtDia.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                txtDia.setTextAppearance(R.style.AppTheme_tbl_titulo);
                titulo.addView(txtDia);
            }

            tableLayout.addView(titulo, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            for (HorarioOff h : horarioOffs) {
                TableRow corpo = new TableRow(this);
                corpo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                for (int i = 0; i < cicloOff.getNumMaterias(); i++) {

                    DisciplinaOff d = new DisciplinaOff();
                    for (DisciplinaOff disc : disciplinas) {
                        if (disc.getCodigo() == h.getDisciplinaCodigo())
                            d = disc;
                    }

                    TextView txtHorario = new TextView(this);
                    txtHorario.setText(d.getNome());
                    txtHorario.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    txtHorario.setTextAppearance(R.style.AppTheme_tbl_corpo);
                    corpo.addView(txtHorario);
                }
                tableLayout.addView(corpo, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    private List<String> ordenarDias(List<HorarioOff> horarioOffs) {
        List<String> dias = new ArrayList<>();
        boolean possui = false;


        for (HorarioOff h : horarioOffs) {
            if (dias.size() > 0) {
                for (int i = 0; i < dias.size(); i++) {
                    if (dias.get(i).equals(h.getDia())) {
                        possui = true;
                    }
                }

                if (!possui) {
                    dias.add(h.getDia());
                }

            } else {
                dias.add(h.getDia());
            }
        }


        return dias;
    }

    @OnClick(R.id.btnEditarCiclo)
    public void editarCiclo(View view) {
        Intent intent = new Intent(CicloDeEstudosActivity.this, CicloDeEstudosCrudActivity.class);
        intent.putExtra("ciclo", cicloOff);
        startActivity(intent);
    }

    private class BuscarCiclo extends AsyncTask<Void, Void, CicloOff> {
        @Override
        protected CicloOff doInBackground(Void... voids) {
            try {
                long codigo = Util.getLocalUserCodigo(CicloDeEstudosActivity.this);
                List<HorarioOff> horarios = HorarioRepositoryOff.listarPorUsuario(codigo, CicloDeEstudosActivity.this);
                CicloOff c = CicloRepositoryOff.buscarPorUsario(codigo, CicloDeEstudosActivity.this);
                c.setHorarios(horarios);
                return c;
            } catch (Exception e) {
                Log.e("ERRO BUSCAR CICLO", e.getMessage());
                return null;
            }
        }
    }

    private class BuscarDisciplinas extends AsyncTask<Void, Void, List<DisciplinaOff>> {
        @Override
        protected List<DisciplinaOff> doInBackground(Void... voids) {
            try {
                return DisciplinaRepositoryOff.listarDisciplinas(Util.getLocalUserCodigo(CicloDeEstudosActivity.this), CicloDeEstudosActivity.this);
            } catch (Exception e) {
                Log.e("ERRO ASYNC DISC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}
