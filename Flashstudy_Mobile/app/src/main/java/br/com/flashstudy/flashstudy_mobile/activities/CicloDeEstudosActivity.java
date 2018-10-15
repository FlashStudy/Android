package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.activities.crud.CicloDeEstudosCrudActivity;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CicloRepositoryOff;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CicloDeEstudosActivity extends AppCompatActivity {

    CicloOff cicloOff;

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

    private void populaTela(){
        try{
            cicloOff = new BuscarCiclo().execute().get();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Houve um erro ao buscar o ciclo!", Toast.LENGTH_LONG).show();
            finish();
        }

        if(cicloOff == null){
            Toast.makeText(getApplicationContext(), "Nenhum ciclo cadastrado!", Toast.LENGTH_LONG).show();
            cicloOff = new CicloOff();
        }else{

        }
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
                return CicloRepositoryOff.buscarPorUsario(codigo, CicloDeEstudosActivity.this);
            } catch (Exception e) {
                Log.i("ERRO BUSCAR CICLO", e.getMessage());
                return null;
            }
        }
    }
}
