package br.com.flashstudy.flashstudy_mobile.activities.crud;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import br.com.flashstudy.flashstudy_mobile.Util.ConversaoDeClasse;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.adapter.CronogramaAdapter;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CronogramaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.DisciplinaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;
import br.com.flashstudy.flashstudy_mobile.online.repository.CronogramaRepository;
import br.com.flashstudy.flashstudy_mobile.online.repository.DisciplinaRepository;
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

    private CronogramaRepository cronogramaRepository = new CronogramaRepository();
    private CronogramaRepositoryOff cronogramaRepositoryOff;
    private DisciplinaRepository disciplinaRepository = new DisciplinaRepository();
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
        CronogramaAdapter adapter = new CronogramaAdapter(this, this, disciplinaOffs);
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

                    //Atualizar
                    case 0:
                        if (isConectado()) {
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
                                                    Usuario usuario = ConversaoDeClasse.usuarioOffToUsuario(Util.getLocalUser(CronogramaCrudActivity.this, codigoUsuario));
                                                    if (disciplinaRepository.atualizar(ConversaoDeClasse.disciplinaOffToDisciplina(disciplinaOff, usuario))) {
                                                        disciplinaRepositoryOff.atualizar(disciplinaOff);
                                                        disciplinaOffs.get(position).setNome(disciplinaOff.getNome());
                                                    } else
                                                        Toast.makeText(CronogramaCrudActivity.this, "Houve um erro!", Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    Toast.makeText(CronogramaCrudActivity.this, "Houve um erro ao atualizar a disciplina", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                disciplinaOffs.get(position).setNome(disciplinaOff.getNome());
                                            }
                                            populaLista();
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(CronogramaCrudActivity.this);
                            dlg.setTitle("AVISO!");
                            dlg.setMessage("Só é possível atualizar quando há uma conexão com a internet!");

                            dlg.setNeutralButton("Ok", null);
                            dlg.show();
                        }


                        break;

                    //deletar
                    case 1:
                        if (isConectado()) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(CronogramaCrudActivity.this);
                            dlg.setTitle("AVISO!");
                            dlg.setMessage("Tem certeza em deletar essa disciplina?");

                            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (disciplinaOff.getCodigo() != 0) {
                                        try {
                                            if (disciplinaRepository.deletar(disciplinaOff.getCodigo())) {
                                                disciplinaRepositoryOff.deletar(disciplinaOff);
                                                disciplinaOffs.remove(position);
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(CronogramaCrudActivity.this, "Ocorreu um erro ao deletar a disciplina!", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        disciplinaOffs.remove(position);
                                    }
                                    populaLista();
                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(CronogramaCrudActivity.this);
                            dlg.setTitle("AVISO!");
                            dlg.setMessage("Só é possível deletar quando há uma conexão com a internet!");

                            dlg.setNeutralButton("Ok", null);
                            dlg.show();
                        }

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @OnClick(R.id.fab)
    public void salvarCronograma() {

        if (isConectado()) {
            long codigoinicial = cronograma.getCodigo();

            cronograma.setUsuarioCodigo(codigoUsuario);
            cronograma.setDisciplinas(disciplinaOffs);
            cronograma.setInicio(txtInicio.getText().toString().trim());
            cronograma.setFim(txtFim.getText().toString().trim());

            Usuario usuario = ConversaoDeClasse.usuarioOffToUsuario(Util.getLocalUser(this, Util.getLocalUserCodigo(this)));

            try {
                List<Disciplina> disciplinas = new ArrayList<>();

                //Prepara as disciplinas para salvar no servidor
                for (DisciplinaOff d : disciplinaOffs) {
                    disciplinas.add(ConversaoDeClasse.disciplinaOffToDisciplina(d, usuario));
                }

                //Prepara o cronograma para salvar no servidor
                Cronograma cronograma1 = ConversaoDeClasse.cronogramaOffToCronograma(cronograma, usuario, disciplinas);

                //Salva o cronograma no servidor
                Cronograma cronograma2 = cronogramaRepository.salvar(cronograma1);

                //Disciplinas com código
                disciplinas = cronograma2.getDisciplinas();

                //Prepara para salvar no dispositivo
                cronograma = ConversaoDeClasse.cronogramaToCronogramaOff(cronograma2);

                disciplinaOffs = new ArrayList<>();

                //Prepara para salvar no dispositivo
                for (Disciplina d : disciplinas) {
                    disciplinaOffs.add(ConversaoDeClasse.disciplinaToDisciplinaOff(d));
                }
                cronograma.setDisciplinas(disciplinaOffs);

                if (codigoinicial == 0) {
                    cronogramaRepositoryOff.salvar(cronograma);
                } else {
                    cronogramaRepositoryOff.atualizar(cronograma);
                }

                Log.e("CRONOGRAMA", cronograma1.toString());

                Toast.makeText(CronogramaCrudActivity.this, "Cronograma salvo com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(CronogramaCrudActivity.this, "Houve um erro ao salvar o cronograma!", Toast.LENGTH_LONG).show();
            }
        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("AVISO!");
            dlg.setMessage("Só é possível salvar quando há uma conexão com a internet!");

            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }


    }

    @OnClick(R.id.btnAddMateria)
    public void addMateria() {

        if (isConectado()) {
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
        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("AVISO!");
            dlg.setMessage("Só é possível adicionar disciplinas quando há uma conexão com a internet!");

            dlg.setNeutralButton("Ok", null);
            dlg.show();
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

    private boolean isConectado() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
