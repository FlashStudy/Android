package br.com.flashstudy.flashstudy_mobile.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.activities.crud.CronogramaCrudActivity;
import br.com.flashstudy.flashstudy_mobile.activities.crud.FlashcardCrudActivity;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;
import br.com.flashstudy.flashstudy_mobile.online.repository.CronogramaRepository;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CronogramaActivity extends ListActivity {

    private Cronograma cronograma = new Cronograma();
    private CronogramaRepository repository = new CronogramaRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma);

        ButterKnife.bind(this);

        try{
        }catch (Exception e){

        }
    }

    @OnClick(R.id.fab)
    public void chamarTelaFlashcardCrud() {
        Intent intent = new Intent(CronogramaActivity.this, CronogramaCrudActivity.class);
        intent.putExtra("cronograma", cronograma);
        startActivity(intent);

    }
}
