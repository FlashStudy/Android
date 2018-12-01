package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.UsuarioRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;
import br.com.flashstudy.flashstudy_mobile.online.repository.UsuarioRepository;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindViews({R.id.txtEmail, R.id.txtSenha})
    public List<EditText> campos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Util.getLocalUserCodigo(MainActivity.this) != 0) {
            Intent intent = new Intent(MainActivity.this, TelaPrincipalActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.btnLogin)
    public void efetuarLogin() {
        if (validaCampos()) {

            UsuarioOff usuarioOff = new UsuarioOff();
            usuarioOff.setEmail(campos.get(0).getText().toString());
            usuarioOff.setSenha(campos.get(1).getText().toString());

            UsuarioRepositoryOff usuarioRepositoryOff = new UsuarioRepositoryOff(this);
            UsuarioRepository usuarioRepository = new UsuarioRepository();

            long codigo = 0;
            boolean encontrado = false;

            try {
                codigo = usuarioRepositoryOff.login(usuarioOff);

                if (codigo == 0) {
                    UsuarioOff usuarioOff1 = usuarioRepository.login(new Usuario(usuarioOff.getEmail(), usuarioOff.getSenha()));

                    if (usuarioOff != null) {
                        usuarioRepositoryOff.salvar(usuarioOff1);
                        codigo = usuarioOff1.getCodigo();
                        encontrado = true;
                    }
                } else {
                    encontrado = true;
                }
            } catch (Exception e) {
                Log.e("ERRO NO LOGIN", e.getMessage());
                e.printStackTrace();
            }

            if (encontrado) {
                Util.setLocalUserCodigo(this, codigo);
                Intent intent = new Intent(this, TelaPrincipalActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Nenhum usuário encontrado!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @OnClick(R.id.txtCadLog)
    public void chamarTelaCadastro() {
        Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    private boolean validaCampos() {

        boolean res = false;

        String email = campos.get(0).getText().toString();
        String senha = campos.get(1).getText().toString();

        if (res = !Util.isEmailValido(email)) {
            campos.get(0).requestFocus();
        } else {
            if (res = Util.isCampoVazio(senha)) {
                campos.get(2).requestFocus();
            } else {
                return true;
            }
        }

        if (!res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("AVISO!");
            dlg.setMessage("Há campos inválidos ou em branco!");

            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        return false;
    }
}