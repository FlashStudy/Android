package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.ConversaoDeClasse;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CicloRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Ciclo;
import br.com.flashstudy.flashstudy_mobile.online.model.DiaDaSemana;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;
import br.com.flashstudy.flashstudy_mobile.online.repository.CicloRepository;
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

    private CicloRepository cicloRepository = new CicloRepository();
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


    // Ciclo
    // Dias
    // Horarios

    @OnClick(R.id.btnSalvarCiclo)
    public void salvarCiclo(View view) {

        if (isConectado()) {
            List<String> dias = new ArrayList<>();
            for (CheckBox cb : cbDias) {
                if (cb.isChecked()) {
                    dias.add(cb.getText().toString());
                }
            }

            Usuario usuario = ConversaoDeClasse.usuarioOffToUsuario(Util.getLocalUser(this, usuarioCodigo));

            cicloOff.setNumMaterias(spinnerNumMaterias.getSelectedItemPosition() + 1);

            Set<DiaDaSemana> semanaSet = new HashSet<>();

            for (String nomeDia : dias) {
                DiaDaSemana diaDaSemana = new DiaDaSemana();
                diaDaSemana.setNome(nomeDia);
                diaDaSemana.setUsuario(usuario);
                semanaSet.add(diaDaSemana);
            }

            Ciclo ciclo = new Ciclo();
            ciclo.setCodigo(cicloOff.getCodigo());
            ciclo.setNumMaterias(cicloOff.getNumMaterias());
            ciclo.setDias(semanaSet);
            ciclo.setUsuario(usuario);

            try {
                Ciclo cicloSalvo = cicloRepository.salvar(ciclo);

                if (cicloRepositoryOff.salvar(cicloSalvo)) {
                    finish();
                } else {
                    Toast.makeText(this, "Ocorreu um erro ao salvar o ciclo!", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(this, "Ocorreu um erro ao salvar o ciclo!", Toast.LENGTH_LONG).show();
            }
        } else {
            AlertDialog.Builder dlg1 = new AlertDialog.Builder(this);
            dlg1.setTitle("AVISO!");
            dlg1.setMessage("Só é possível salvar quando há uma conexão com a internet!");
            dlg1.setNeutralButton("Ok", null);
            dlg1.show();
        }

    }

    private boolean isConectado() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
