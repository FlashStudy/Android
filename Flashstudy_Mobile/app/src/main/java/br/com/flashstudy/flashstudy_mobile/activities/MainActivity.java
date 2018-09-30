package br.com.flashstudy.flashstudy_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.repository.UsuarioRepository;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindViews({R.id.txtEmail, R.id.txtSenha})
    public List<EditText> campos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int codigo = 0;

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
            codigo = sharedPreferences.getInt("codigo", 0);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (codigo != 0) {
            Intent intent = new Intent(MainActivity.this, TelaPrincipalActivity.class);
            startActivity(intent);
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

            UsuarioRepository usuarioRepository = new UsuarioRepository();

            try {
                int res = usuarioRepository.login(usuarioOff, getApplicationContext());
                if(res != 0){
                    usuarioOff.setCodigo(res);

                    SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("codigo", usuarioOff.getCodigo());
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, TelaPrincipalActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("AVISO!");
                    builder.setMessage("Nenhum registro foi encontrado!");
                    builder.setNeutralButton("OK", null);
                    builder.show();
                }
                Toast.makeText(getApplicationContext(), String.valueOf(res), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.i("ERRO NO LOGIN", e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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

        if (res = !isEmailValido(email)) {
            campos.get(0).requestFocus();
        } else {
            if (res = isCampoVazio(senha)) {
                campos.get(2).requestFocus();
            } else {
                return true;
            }
        }

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("AVISO!");
            dlg.setMessage("Há campos inválidos ou em branco!");

            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        return false;
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    private boolean isEmailValido(String email) {
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }
}
