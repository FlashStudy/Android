package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CronogramaCrudActivity extends AppCompatActivity {

    @BindView(R.id.txtInicio)
    EditText txtInicio;

    @BindView(R.id.txtFim)
    EditText txtFim;

    @BindView(R.id.txtDisciplina)
    EditText txtDisciplina;

    @BindView(R.id.swipeMenuDisciplinas)
    SwipeMenuListView swipeMenuListView;

    Cronograma cronograma;

    List<Disciplina> disciplinas;
    List<Disciplina> disciplinasInicial = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma_crud);


        ButterKnife.bind(this);

        Intent intent;

        try {
            intent = getIntent();
            cronograma = (Cronograma) intent.getSerializableExtra("cronograma");
        } catch (Exception e) {
            Toast.makeText(CronogramaCrudActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }

        if (cronograma != null) {
            for (Disciplina d : cronograma.getDisciplinas()) {
                disciplinasInicial.add(d);
            }

            disciplinas = disciplinasInicial;


            ArrayAdapter adapter = new ArrayAdapter(CronogramaCrudActivity.this, android.R.layout.simple_list_item_1, disciplinas);
            swipeMenuListView.setAdapter(adapter);

            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getApplicationContext());
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                            0, 0)));
                    // set item width
                    deleteItem.setWidth(170);
                    // set a icon
                    deleteItem.setIcon(R.drawable.ic_delete);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };

            // set creator
            swipeMenuListView.setMenuCreator(creator);

            swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {

                        //deletar
                        case 0:
                            AlertDialog.Builder dlg = new AlertDialog.Builder(CronogramaCrudActivity.this);
                            dlg.setTitle("AVISO!");
                            dlg.setMessage("Tem certeza em deletar essa disciplina?");

                            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                /*try {
                                    boolean result = flashcardRepositoryOff.deletar(flashcardOff, getApplicationContext());
                                    if (result) {
                                        Toast.makeText(getApplicationContext(), "Flashcard deletado com sucesso!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Houve um erro ao deletar o flashcard!", Toast.LENGTH_LONG).show();
                                }*/
                                }
                            });
                            dlg.show();
                            break;
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });
        }else {
            cronograma = new Cronograma();
        }
    }

    @OnClick(R.id.fab)
    public void salvarCronograma() {

    }
}
