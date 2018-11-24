package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CicloRepositoryOff;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CicloDeEstudosCrudActivity extends AppCompatActivity {

    @BindViews({R.id.cbDomingo, R.id.cbSegunda, R.id.cbTerca, R.id.cbQuarta, R.id.cbQuinta, R.id.cbSexta, R.id.cbSabado})
    public List<CheckBox> cbDias;

    @BindView(R.id.spinNumMaterias)
    public Spinner spinnerNumMaterias;

    private long usuarioCodigo;

    private CicloOff cicloOff;

    private CicloRepositoryOff cicloRepositoryOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_de_estudos_crud);

        cicloRepositoryOff = new CicloRepositoryOff(this);

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

        if (cicloOff.getCodigo() != 0) {
            spinnerNumMaterias.setSelection(cicloOff.getNumMaterias() - 1);

            for (HorarioOff h : cicloOff.getHorarios()) {
                for (CheckBox cb : cbDias) {
                    if (cb.getText().toString().equals(h.getDia())) {
                        if (!cb.isChecked()) {
                            cb.setChecked(true);
                        }
                    }
                }
            }
        } else {
            cicloOff = new CicloOff();
        }

    }

    @OnClick(R.id.btnSalvarCiclo)
    public void salvarCiclo(View view) {

        List<String> dias = new ArrayList<>();
        for (CheckBox cb : cbDias) {
            if (cb.isChecked()) {
                dias.add(cb.getText().toString());
            }
        }

        cicloOff.setNumMaterias(spinnerNumMaterias.getSelectedItemPosition() + 1);

        try {
            cicloRepositoryOff.salvar(cicloOff, dias);
            Toast.makeText(this, "Ciclo salvo com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Ocorreu um erro ao salvar o ciclo!", Toast.LENGTH_LONG).show();
        }


    }

}
