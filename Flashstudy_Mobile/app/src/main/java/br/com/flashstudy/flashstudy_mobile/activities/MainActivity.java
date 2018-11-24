package br.com.flashstudy.flashstudy_mobile.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

            new Login().execute(usuarioOff);

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

    private class Login extends AsyncTask<UsuarioOff, Void, Void> {
        ProgressDialog progressDialog;
        boolean encontrado;
        long codigo;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Efetuando login");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(UsuarioOff... usuarioOffs) {
            UsuarioRepositoryOff usuarioRepositoryOff = new UsuarioRepositoryOff(MainActivity.this);
            UsuarioRepository usuarioRepository = new UsuarioRepository();

            try {
                codigo = usuarioRepositoryOff.login(usuarioOffs[0]);

                if (codigo == 0) {
                    UsuarioOff usuarioOff = usuarioRepository.login(new Usuario(usuarioOffs[0].getEmail(), usuarioOffs[0].getSenha()));

                    if (usuarioOff != null) {
                        usuarioRepositoryOff.salvar(usuarioOff);
                        codigo = usuarioOff.getCodigo();
                        encontrado = true;
                    }
                } else {
                    encontrado = true;
                }
            } catch (Exception e) {
                Log.i("ERRO NO LOGIN", e.getMessage());
                cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();

            if (encontrado) {
                Util.setLocalUserCodigo(MainActivity.this, codigo);

                Intent intent = new Intent(MainActivity.this, TelaPrincipalActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Nenhum usuário encontrado!", Toast.LENGTH_LONG).show();
            }
        }
    }
}