package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CronogramaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.DisciplinaRepositoryOff;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CronogramaCrudActivity extends AppCompatActivity {

    @BindView(R.id.txtInicio)
    public EditText txtInicio;

    @BindView(R.id.txtFim)
    public EditText txtFim;

    @BindView(R.id.txtDisciplina)
    public EditText txtDisciplina;

    @BindView(R.id.swipeMenuDisciplinas)
    public SwipeMenuListView swipeMenuListView;

    private CronogramaRepositoryOff cronogramaRepositoryOff;
    private DisciplinaRepositoryOff disciplinaRepositoryOff;

    private CronogramaOff cronograma;
    private List<DisciplinaOff> disciplinaOffs = new ArrayList<>();

    private long codigoUsuario;

    private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma_crud);

        cronogramaRepositoryOff = new CronogramaRepositoryOff(this);
        disciplinaRepositoryOff = new DisciplinaRepositoryOff(this);

        ButterKnife.bind(this);

        codigoUsuario = Util.getLocalUserCodigo(CronogramaCrudActivity.this);

        Intent intent;

        try {
            intent = getIntent();
            cronograma = (CronogramaOff) intent.getSerializableExtra("cronograma");
        } catch (Exception e) {
            Toast.makeText(CronogramaCrudActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }


        if (cronograma != null) {
            disciplinaOffs.addAll(cronograma.getDisciplinas());
            txtFim.setText(cronograma.getFim());
            txtInicio.setText(cronograma.getInicio());

            populaLista();

        } else {
            cronograma = new CronogramaOff();
            txtInicio.setText(data(myCalendar.getTime()));
        }
    }

    public void populaLista() {
        ArrayAdapter adapter = new ArrayAdapter(CronogramaCrudActivity.this, android.R.layout.simple_list_item_1, disciplinaOffs);
        swipeMenuListView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0,
                        0, 255)));
                // set item width
                editItem.setWidth(170);
                // set a icon
                editItem.setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(editItem);


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
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                final DisciplinaOff disciplinaOff = disciplinaOffs.get(position);
                switch (index) {

                    case 0:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CronogramaCrudActivity.this);
                        alertDialog.setTitle("Editar disciplina");
                        alertDialog.setMessage(R.string.lbl_disciplina);
                        final EditText input = new EditText(CronogramaCrudActivity.this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        input.setText(disciplinaOff.getNome());
                        alertDialog.setView(input);
                        alertDialog.setIcon(R.drawable.ic_edit);

                        alertDialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        disciplinaOff.setNome(input.getText().toString().trim());

                                        if (disciplinaOff.getCodigo() != 0) {
                                            try {
                                                disciplinaRepositoryOff.atualizar(disciplinaOff);
                                            } catch (Exception e) {
                                                Toast.makeText(CronogramaCrudActivity.this, "Houve um erro ao atualizar a disciplina", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        disciplinaOffs.get(position).setNome(disciplinaOff.getNome());
                                        populaLista();
                                    }
                                });
                        alertDialog.show();

                        break;

                    //deletar
                    case 1:
                        AlertDialog.Builder dlg = new AlertDialog.Builder(CronogramaCrudActivity.this);
                        dlg.setTitle("AVISO!");
                        dlg.setMessage("Tem certeza em deletar essa disciplina?");

                        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (disciplinaOff.getCodigo() != 0) {

                                    try {
                                        disciplinaRepositoryOff.deletar(disciplinaOff);
                                    } catch (Exception e) {
                                        Toast.makeText(CronogramaCrudActivity.this, "Ocorreu um erro ao deletar a disciplina!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                disciplinaOffs.remove(position);
                                populaLista();
                                Toast.makeText(CronogramaCrudActivity.this, "Disciplina deletada com sucesso!", Toast.LENGTH_LONG).show();
                            }
                        });
                        dlg.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @OnClick(R.id.fab)
    public void salvarCronograma() {

        cronograma.setUsuarioCodigo(codigoUsuario);
        cronograma.setDisciplinas(disciplinaOffs);
        cronograma.setInicio(txtInicio.getText().toString().trim());
        cronograma.setFim(txtFim.getText().toString().trim());

        try {
            if (cronograma.getCodigo() == 0)
                cronogramaRepositoryOff.salvar(cronograma);
            else
                cronogramaRepositoryOff.atualizar(cronograma);

            Toast.makeText(CronogramaCrudActivity.this, "Cronograma salvo com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(CronogramaCrudActivity.this, "Houve um erro ao salvar o cronograma!", Toast.LENGTH_LONG).show();
        }

    }

    @OnClick(R.id.btnAddMateria)
    public void addMateria() {

        if (!Util.isCampoVazio(txtDisciplina.getText().toString())) {

            DisciplinaOff d = new DisciplinaOff();
            d.setNome(txtDisciplina.getText().toString().trim());
            d.setUsuarioCodigo(codigoUsuario);
            txtDisciplina.setText("");

            disciplinaOffs.add(d);
            populaLista();
        } else {
            Toast.makeText(CronogramaCrudActivity.this, "O campo está vazio!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @OnClick({R.id.txtInicio, R.id.txtFim})
    public void setData(final EditText txt) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txt.setText(data(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(CronogramaCrudActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private String data(Date date) {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        return sdf.format(date.getTime());
    }

}
