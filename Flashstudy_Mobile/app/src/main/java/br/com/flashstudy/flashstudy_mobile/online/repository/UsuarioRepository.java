package br.com.flashstudy.flashstudy_mobile.online.repository;

import android.os.AsyncTask;
import android.util.Log;

import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.clients.UsuarioRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class UsuarioRepository {
    private UsuarioRestClient usuarioRestClient = new UsuarioRestClient();

    public UsuarioOff salvar(Usuario usuario) {
        try {
            return new Cadastro().execute(usuario).get();
        } catch (Exception e) {
            Log.e("ERRO REPO USUARIO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public UsuarioOff login(Usuario usuario) {
        try {
            usuario = new Login().execute(usuario).get();
        } catch (Exception e) {
            Log.e("ERRO CONSULTA", e.getMessage());
            e.printStackTrace();
            return null;
        }

        if (usuario != null) {
            return new UsuarioOff(usuario.getCodigo(),
                    usuario.getNome(), usuario.getEmail(), usuario.getSenha());
        }else{
            return null;
        }
    }

    public boolean atualizar(Usuario usuario){
        try {
            return new Atualizar().execute(usuario).get();
        } catch (Exception e) {
            Log.e("ERRO REPO USUARIO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private class Login extends AsyncTask<Usuario, Void, Usuario> {

        @Override
        protected Usuario doInBackground(Usuario... usuarios) {
            try {
                return usuarioRestClient.procuraPorEmail(usuarios[0].getEmail());
            } catch (Exception e) {
                Log.e("ERRO CONSULTA SERVIDOR", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Cadastro extends AsyncTask <Usuario, Void, UsuarioOff>{

        @Override
        protected UsuarioOff doInBackground(Usuario... usuarios) {
            try{
                return usuarioRestClient.cadastro(usuarios[0]);
            }catch (Exception e){
                Log.e("ERRO CONSULTA SERVIDOR", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Atualizar extends AsyncTask<Usuario, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Usuario... usuarios) {
            try{
                return usuarioRestClient.atualizar(usuarios[0]);
            }catch (Exception e){
                Log.e("ERRO CONSULTA SERVIDOR", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }
}