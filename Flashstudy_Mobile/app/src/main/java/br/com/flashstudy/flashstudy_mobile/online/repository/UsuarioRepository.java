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
            return new AdicionaUsuarioAsync().execute(usuario).get();
        } catch (Exception e) {
            return null;
        }
    }

    public UsuarioOff login(Usuario usuario) {
        try {
            return new LoginUsuarioAsync().execute(usuario).get();
        } catch (Exception e) {
            Log.i("ERRO NO LOGIN:", e.getMessage());
            return null;
        }
    }

    private class AdicionaUsuarioAsync extends AsyncTask<Usuario, Void, UsuarioOff> {

        @Override
        protected UsuarioOff doInBackground(Usuario... usuarios) {
            try {
                usuarioRestClient.cadastro(usuarios[0]);
                usuarios[0] = usuarioRestClient.procuraPorEmail(usuarios[0].getEmail());

                UsuarioOff usuarioOff = new UsuarioOff((int) (long) usuarios[0].getCodigo(),
                        usuarios[0].getNome(), usuarios[0].getEmail(), usuarios[0].getSenha());

                Log.i("CADASTRO USUÁRIO", usuarioOff.toString());

                return usuarioOff;
            } catch (Exception e) {
                Log.i("ERRO CADASTRO USUÁRIO", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(UsuarioOff usuarioOff) {
            super.onPostExecute(usuarioOff);
        }
    }

    private class LoginUsuarioAsync extends AsyncTask<Usuario, Void, UsuarioOff> {

        @Override
        protected UsuarioOff doInBackground(Usuario... usuarios) {

            Usuario usuario = new Usuario();
            try {
                usuario = usuarioRestClient.procuraPorEmail(usuarios[0].getEmail());
            } catch (Exception e) {
                Log.i("ERRO CONSULTA SERVIDOR", e.getMessage());
            }

            if (usuario == null) {
                return null;
            } else {
                return new UsuarioOff((int) (long) usuario.getCodigo(),
                        usuario.getNome(), usuario.getEmail(), usuario.getSenha());

            }
        }

        @Override
        protected void onPostExecute(UsuarioOff usuarioOff) {
            super.onPostExecute(usuarioOff);
        }

    }
}