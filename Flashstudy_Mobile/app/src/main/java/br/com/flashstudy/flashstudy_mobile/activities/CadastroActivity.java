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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;
import br.com.flashstudy.flashstudy_mobile.repository.UsuarioRepository;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroActivity extends AppCompatActivity {

    @BindViews({R.id.txtNome, R.id.txtEmail, R.id.txtSenha, R.id.txtConfirmeSenha})
    public List<EditText> campos;

    @BindView(R.id.btnCad)
    public Button btn;

    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCad)
    public void cadastrar() {
        validaCampos();
    }

    private void validaCampos() {

        boolean res = false;

        String nome = campos.get(0).getText().toString();
        String email = campos.get(1).getText().toString();
        String senha = campos.get(2).getText().toString();
        String confirmaSenha = campos.get(3).getText().toString();

        if (res = isCampoVazio(nome)) {
            campos.get(0).requestFocus();
        } else {
            if (res = isCampoVazio(senha)) {
                campos.get(2).requestFocus();
            } else {
                if (res = isCampoVazio(confirmaSenha)) {
                    campos.get(3).requestFocus();
                } else {
                    if (res = !isEmailValido(email)) {
                        campos.get(1).requestFocus();
                    } else {
                        if (!senha.equals(confirmaSenha)) {
                            campos.get(2).requestFocus();
                        } else {
                            Usuario usuario = new Usuario();
                            usuario.setEmail(email);
                            usuario.setNome(nome);
                            usuario.setSenha(senha);
                            try {
                                UsuarioOff usuarioOff = usuarioRepository.save(usuario, getApplicationContext());

                                SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("codigo", usuarioOff.getCodigo());
                                editor.apply();

                                Intent intent = new Intent(CadastroActivity.this, TelaPrincipalActivity.class);
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), usuarioOff.toString(), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.i("ERRO NO CADASTRO", e.getMessage());
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        }

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("AVISO!");
            dlg.setMessage("Há campos inválidos ou em branco!");

            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
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
