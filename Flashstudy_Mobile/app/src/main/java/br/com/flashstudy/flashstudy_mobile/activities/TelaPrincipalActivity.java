package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.Sincronizacao.Sincronizar;
import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.PastaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Pasta;
import br.com.flashstudy.flashstudy_mobile.online.repository.PastaRepository;
import br.com.flashstudy.flashstudy_mobile.service.NotificationService;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TelaPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        ButterKnife.bind(this);

        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);

        if (!isConectado()) {
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        } else {
            boolean sincronizacao = false;
            try {
                Log.e("INICIANDO SINCRO", "INICIANDO");
                sincronizacao = new Sincronizar(this).sincronizar();

                if (new PastaRepositoryOff(this).listar(Util.getLocalUserCodigo(this)).size() == 0){
                    PastaOff pastaOff = new PastaOff("Sem categoria", Util.getLocalUserCodigo(this));
                    new PastaRepositoryOff(this).salvar(pastaOff);
                }

            } catch (Exception e) {
                Log.e("ERRO SINCRO", e.getMessage());
                e.printStackTrace();
            }

            if (!sincronizacao) {
                Toast.makeText(this, "Ocorreu um erro durante a sincronização", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.imageViewCiclo, R.id.textViewCiclo})
    public void chamarTelaCicloDeEstudos() {
        Intent intent = new Intent(TelaPrincipalActivity.this, CicloDeEstudosActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.imageViewCronograma, R.id.textViewCronograma})
    public void chamarTelaCronograma() {
        Intent intent = new Intent(TelaPrincipalActivity.this, CronogramaActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.imageViewFlashcard, R.id.textViewFlashcards})
    public void chamarTelaFlashcard() {
        Intent intent = new Intent(TelaPrincipalActivity.this, FlashcardActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.imageViewPerfil, R.id.textViewPerfil})
    public void chamarTelaPerfil() {
        Intent intent = new Intent(TelaPrincipalActivity.this, PerfilActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.imageViewAjuda, R.id.textViewAjuda})
    public void chamarTelaAjuda() {
        Intent intent = new Intent(TelaPrincipalActivity.this, AjudaActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.imgSair)
    public void sair() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Confirmação");
        dlg.setMessage("Tem certeza em deslogar do aplicativo? \n\n OBS: Seus dados não serão perdidos!");

        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Util.setLocalUserCodigo(TelaPrincipalActivity.this, 0);

                Intent intent = new Intent(TelaPrincipalActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dlg.show();
    }

    private boolean isConectado() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
