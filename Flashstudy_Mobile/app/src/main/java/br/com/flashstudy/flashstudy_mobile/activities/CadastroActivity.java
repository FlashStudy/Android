package br.com.flashstudy.flashstudy_mobile.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.UsuarioRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;
import br.com.flashstudy.flashstudy_mobile.online.repository.UsuarioRepository;
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
    private UsuarioRepositoryOff usuarioRepositoryOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        usuarioRepositoryOff = new UsuarioRepositoryOff(this);

        ButterKnife.bind(this);

        if(!isConectado()){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("AVISO!");
            dlg.setMessage("Não há conexão com a internet!");

            dlg.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            dlg.show();
        }else{

        }
    }

    @OnClick(R.id.btnCad)
    public void validarCampos() {

        boolean res;

        String nome = campos.get(0).getText().toString();
        String email = campos.get(1).getText().toString();
        String senha = campos.get(2).getText().toString();
        String confirmaSenha = campos.get(3).getText().toString();

        if (res = Util.isCampoVazio(nome)) {
            campos.get(0).requestFocus();
        } else {
            if (res = Util.isCampoVazio(senha)) {
                campos.get(2).requestFocus();
            } else {
                if (res = Util.isCampoVazio(confirmaSenha)) {
                    campos.get(3).requestFocus();
                } else {
                    if (res = !Util.isEmailValido(email)) {
                        campos.get(1).requestFocus();
                    } else {
                        if (!senha.equals(confirmaSenha)) {
                            campos.get(2).requestFocus();
                        } else {
                            try {

                                Usuario usuario = new Usuario();
                                usuario.setNome(nome);
                                usuario.setEmail(email);
                                usuario.setSenha(senha);

                                UsuarioOff usuarioOff = usuarioRepository.salvar(usuario);

                                usuarioRepositoryOff.salvar(usuarioOff);

                                Util.setLocalUserCodigo(CadastroActivity.this, usuarioOff.getCodigo());

                                Intent intent = new Intent(CadastroActivity.this, TelaPrincipalActivity.class);
                                startActivity(intent);
                                finish();

                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    private boolean isConectado() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
