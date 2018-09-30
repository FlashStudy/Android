package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.flashstudy.flashstudy_mobile.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TelaPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        ButterKnife.bind(this);
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

    /*@OnClick(R.id.imageViewSair)
    public void sair() {
    }*/
}
