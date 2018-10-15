package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DiaDaSemanaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CicloRepositoryOff;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CicloDeEstudosCrudActivity extends AppCompatActivity {

    CicloOff cicloOff;

    @BindViews({R.id.cbDomingo, R.id.cbSegunda, R.id.cbTerca, R.id.cbQuarta, R.id.cbQuinta, R.id.cbSexta, R.id.cbSabado})
    List<CheckBox> cbDias;

    @BindView(R.id.spinNumMaterias)
    Spinner spinnerNumMaterias;

    long usuarioCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_de_estudos_crud);

        usuarioCodigo = Util.getLocalUserCodigo(this);

        ButterKnife.bind(this);

        try {
            Intent intent;
            intent = getIntent();
            cicloOff = (CicloOff) intent.getSerializableExtra("ciclo");
        } catch (Exception e) {
            Toast.makeText(this, "Houve um erro com o ciclo!", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (cicloOff != null) {
            spinnerNumMaterias.setSelection((int) cicloOff.getNumMaterias() - 1);

            for (DiaDaSemanaOff d : cicloOff.getDias()) {
                for (int i = 0; i < cbDias.size(); i++) {
                    if (cbDias.get(i).getText().toString().equals(d.getNome())) {
                        cbDias.get(i).setChecked(true);
                    }
                }
            }
        } else {
            cicloOff = new CicloOff();
        }
    }

    @OnClick(R.id.btnSalvarCiclo)
    public void salvarCiclo(View view) {
        List<DiaDaSemanaOff> dias = new ArrayList<>();

        for (CheckBox cb : cbDias) {
            if (cb.isChecked()) {
                DiaDaSemanaOff d = new DiaDaSemanaOff();
                d.setUsuarioCodigo(usuarioCodigo);
                d.setNome(cb.getText().toString());
                dias.add(d);
            }
        }

        cicloOff.setDias(dias);
        cicloOff.setNumMaterias(spinnerNumMaterias.getSelectedItemPosition() + 1);

        try {
            new Salvar().execute(cicloOff);
            Toast.makeText(this, "Ciclo salvo com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Ocorreu um erro ao salvar o ciclo!", Toast.LENGTH_LONG).show();
        }
    }

    private class Salvar extends AsyncTask<CicloOff, Void, Void> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(CicloDeEstudosCrudActivity.this);
            dialog.setMessage("Salvando e gerando ciclo.../");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(CicloOff... cicloOffs) {
            try {
                CicloRepositoryOff.salvar(cicloOffs[0], CicloDeEstudosCrudActivity.this);
            } catch (Exception e) {
                Log.i("ERRO SALVAR CICLO", e.getMessage());
                dialog.dismiss();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
        }
    }

    private class Atualizar extends AsyncTask<CicloOff, Void, Void> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(CicloDeEstudosCrudActivity.this);
            dialog.setMessage("Gerando ciclo.../");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(CicloOff... cicloOffs) {
            try {

            } catch (Exception e) {
                Log.i("ERRO SALVAR CICLO", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            finish();
        }
    }
}