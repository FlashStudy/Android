package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.UsuarioRepositoryOff;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerfilActivity extends AppCompatActivity {

    @BindViews({R.id.txtNome, R.id.txtSenha, R.id.txtConfirmeSenha})
    List<EditText> campos;

    @BindViews({R.id.btnConfirmar, R.id.btnCancelar})
    List<Button> btns;

    @BindView(R.id.txtEmail)
    EditText txtEmail;

    UsuarioOff usuarioOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        long codigo = 0;

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
            codigo = sharedPreferences.getLong("codigo", 0);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        UsuarioRepositoryOff usuarioRepositoryOff = new UsuarioRepositoryOff();

        usuarioOff = usuarioRepositoryOff.getLocaluserById(codigo, getApplicationContext());

        ButterKnife.bind(this);
        ButterKnife.apply(campos, ENABLED, false);
        txtEmail.setText(usuarioOff.getEmail());
        resetaCampos();
    }

    static final ButterKnife.Setter<EditText, Boolean> ENABLED = new ButterKnife.Setter<EditText, Boolean>() {
        @Override
        public void set(@NonNull EditText view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };

    static final ButterKnife.Setter<Button, Boolean> VISIBILITY = new ButterKnife.Setter<Button, Boolean>() {
        @Override
        public void set(@NonNull Button view, Boolean value, int index) {
            if (value) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    };

    @OnClick(R.id.btnEditar)
    public void editar() {
        ButterKnife.apply(campos, ENABLED, true);
        ButterKnife.apply(btns, VISIBILITY, true);
    }

    @OnClick(R.id.btnCancelar)
    public void cancelar() {
        ButterKnife.apply(campos, ENABLED, false);
        ButterKnife.apply(btns, VISIBILITY, false);
        resetaCampos();
    }

    private void resetaCampos() {
        campos.get(0).setText(usuarioOff.getNome());
        campos.get(1).setText("");
        campos.get(1).setText("");
    }

    ;
}
