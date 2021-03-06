package br.com.flashstudy.flashstudy_mobile.activities.crud;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.ConversaoDeClasse;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.adapter.AssuntoListAdapter;
import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.AssuntoRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Assunto;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;
import br.com.flashstudy.flashstudy_mobile.online.repository.AssuntoRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisciplinaCrudActivity extends AppCompatActivity {

    @BindView(R.id.txtAssunto)
    public EditText txtAssunto;

    @BindView(R.id.lblDisciplina)
    public TextView textViewDisciplina;

    @BindView(R.id.swipeMenuAssuntos)
    public SwipeMenuListView swipeMenuListView;

    private AssuntoRepository assuntoRepository = new AssuntoRepository();
    private AssuntoRepositoryOff assuntoRepositoryOff;

    private DisciplinaOff disciplinaOff;
    private List<AssuntoOff> assuntos = new ArrayList<>();

    private long codigoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina_crud);

        assuntoRepositoryOff = new AssuntoRepositoryOff(this);

        ButterKnife.bind(this);

        codigoUsuario = Util.getLocalUserCodigo(DisciplinaCrudActivity.this);

        Intent intent;

        try {
            intent = getIntent();
            disciplinaOff = (DisciplinaOff) intent.getSerializableExtra("disciplina");
        } catch (Exception e) {
            Toast.makeText(DisciplinaCrudActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }


        try {
            assuntos = assuntoRepositoryOff.listarPorDisciplinaCodigo(disciplinaOff.getCodigo());
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao buscar disciplinas", Toast.LENGTH_LONG).show();
            finish();
        }

        Log.e("DISCIPLINA", disciplinaOff.toStringCompleto());
        Log.e("ASSUNTOS", assuntos.toString());

        if (disciplinaOff != null) {
            textViewDisciplina.setText(disciplinaOff.getNome());
            populaLista();
        } else {
            disciplinaOff = new DisciplinaOff();
        }

    }


    public void populaLista() {
        AssuntoListAdapter adapter = new AssuntoListAdapter(this, this, assuntos);
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
                switch (index) {

                    //editar
                    case 0:
                        if (isConectado()) {
                            final AssuntoOff a = assuntos.get(position);

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DisciplinaCrudActivity.this);
                            alertDialog.setTitle("Editar assunto");
                            alertDialog.setMessage(R.string.lbl_assunto);
                            final EditText input = new EditText(DisciplinaCrudActivity.this);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            input.setLayoutParams(lp);
                            input.setText(a.getTema());
                            alertDialog.setView(input);
                            alertDialog.setIcon(R.drawable.ic_edit);

                            alertDialog.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            a.setTema(input.getText().toString().trim());

                                            if (a.getCodigo() != 0) {
                                                try {

                                                    Usuario usuario = ConversaoDeClasse.usuarioOffToUsuario(Util.getLocalUser(DisciplinaCrudActivity.this, codigoUsuario));
                                                    Disciplina disciplina = ConversaoDeClasse.disciplinaOffToDisciplina(disciplinaOff, usuario);
                                                    Assunto assunto = ConversaoDeClasse.assuntoOffToAssunto(a, usuario, disciplina);

                                                    if (assuntoRepository.atualizar(assunto)) {
                                                        assuntoRepositoryOff.atualizar(a);
                                                        assuntos.get(position).setTema(a.getTema());
                                                    } else {
                                                        Toast.makeText(DisciplinaCrudActivity.this, "Houve um erro ao atualizar o assunto!", Toast.LENGTH_LONG).show();
                                                    }
                                                } catch (Exception e) {
                                                    Toast.makeText(DisciplinaCrudActivity.this, "Houve um erro ao atualizar o assunto!", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            } else {
                                                assuntos.get(position).setTema(a.getTema());
                                            }
                                            populaLista();
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            AlertDialog.Builder dlg1 = new AlertDialog.Builder(DisciplinaCrudActivity.this);
                            dlg1.setTitle("AVISO!");
                            dlg1.setMessage("Só é possível editar quando há uma conexão com a internet!");
                            dlg1.setNeutralButton("Ok", null);
                            dlg1.show();
                        }


                        break;

                    //deletar
                    case 1:
                        if (isConectado()) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(DisciplinaCrudActivity.this);
                            dlg.setTitle("AVISO!");
                            dlg.setMessage("Tem certeza em deletar esse assunto?");

                            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (assuntos.get(position).getCodigo() != 0) {
                                        try {
                                            if (assuntoRepository.deletar(assuntos.get(position).getCodigo())) {
                                                if (assuntoRepositoryOff.deletar(assuntos.get(position))) {
                                                    assuntos.remove(position);
                                                }
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), "Houve um erro ao deletar a disciplina!", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        assuntos.remove(position);
                                    }
                                    populaLista();
                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg1 = new AlertDialog.Builder(DisciplinaCrudActivity.this);
                            dlg1.setTitle("AVISO!");
                            dlg1.setMessage("Só é possível deletar quando há uma conexão com a internet!");
                            dlg1.setNeutralButton("Ok", null);
                            dlg1.show();
                        }

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @OnClick(R.id.fab)
    public void salvarAssuntos() {
        if (isConectado()) {
            try {

                List<Assunto> assuntosOn = new ArrayList<>();

                Usuario usuario = ConversaoDeClasse.usuarioOffToUsuario(Util.getLocalUser(DisciplinaCrudActivity.this, codigoUsuario));
                Disciplina disciplina = ConversaoDeClasse.disciplinaOffToDisciplina(disciplinaOff, usuario);

                for (AssuntoOff assuntoOff : assuntos) {
                    Assunto assunto = ConversaoDeClasse.assuntoOffToAssunto(assuntoOff, usuario, disciplina);
                    assuntosOn.add(assunto);
                }

                List<Assunto> assuntosSalvos = assuntoRepository.salvar(assuntosOn);

                List<AssuntoOff> assuntoOffsSalvar = new ArrayList<>();

                Log.e("ASSUNTOS ANTES", assuntosOn.toString());
                Log.e("ASSUNTOS", assuntosSalvos.toString());

                for (int i = 0; i < assuntosSalvos.size(); i++) {
                    AssuntoOff assuntoOff = ConversaoDeClasse.assuntoToAssuntoOff(assuntosSalvos.get(i));
                    assuntoOffsSalvar.add(assuntoOff);
                }

                Log.e("ASSUNTOS OFF", assuntoOffsSalvar.get(0).toStringCompleto());

                if (assuntoRepositoryOff.salvarLista(assuntoOffsSalvar)) {
                    Toast.makeText(this, "Assuntos salvos!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASSUNTOS", e.getMessage());
                e.printStackTrace();
                Toast.makeText(DisciplinaCrudActivity.this, "Houve um erro ao salvar o cronograma! Tente novamente!", Toast.LENGTH_LONG).show();
            }
        } else {
            AlertDialog.Builder dlg1 = new AlertDialog.Builder(DisciplinaCrudActivity.this);
            dlg1.setTitle("AVISO!");
            dlg1.setMessage("Só é possível salvar quando há uma conexão com a internet!");
            dlg1.setNeutralButton("Ok", null);
            dlg1.show();
        }

    }

    @OnClick(R.id.btnAddAssunto)
    public void addMateria() {

        if (!Util.isCampoVazio(txtAssunto.getText().toString())) {

            AssuntoOff a = new AssuntoOff();
            a.setTema(txtAssunto.getText().toString().trim());
            a.setDisciplinaCodigo(disciplinaOff.getCodigo());
            a.setUsuarioCodigo(codigoUsuario);

            assuntos.add(a);

            txtAssunto.setText("");

            populaLista();
        } else {
            Toast.makeText(DisciplinaCrudActivity.this, "O campo está vazio!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean isConectado() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
