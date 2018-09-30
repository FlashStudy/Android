package br.com.flashstudy.flashstudy_mobile.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.clients.UsuarioRestClient;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class UsuarioRepository {

    private UsuarioRestClient usuarioRestClient = new UsuarioRestClient();
    private Context context;

    public UsuarioOff getLocaluserById(int codigo, Context context) {
        try {
            this.context = context;
            return new GetLocalUserByIdAsync().execute(codigo).get();
        } catch (Exception e) {
            return null;
        }
    }

    public UsuarioOff save(Usuario usuario, Context context) {
        try {
            this.context = context;
            return new AdicionaUsuarioAsync().execute(usuario).get();
        } catch (Exception e) {
            return null;
        }
    }

    public int login(UsuarioOff usuarioOff, Context context1) {
        try {
            this.context = context1;
            return new LoginUsuarioAsync().execute(usuarioOff).get();
        } catch (Exception e) {
            Log.i("ERRO NO LOGIN:", e.getMessage());
            return 0;
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


                AppDatabase.getAppDatabase(context).usuarioDao().insert(usuarioOff);

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

    private class LoginUsuarioAsync extends AsyncTask<UsuarioOff, Void, Integer> {

        @SuppressLint("NewApi")
        @Override
        protected Integer doInBackground(UsuarioOff... usuarioOffs) {

            UsuarioOff usuarioOff = new UsuarioOff();
            Usuario usuario = new Usuario();

            try {
                usuarioOff = AppDatabase.getAppDatabase(context).usuarioDao().getUsuarioByEmail(usuarioOffs[0].getEmail());

                List<UsuarioOff> offs = AppDatabase.getAppDatabase(context).usuarioDao().getUsuarios();
                Log.i("USUARIOS CADASTRADOS", offs.toString());
            } catch (Exception e) {
                Log.i("ERRO CONSULTA LOCAL", e.getMessage());
            }

            if (usuarioOff == null) {
                try {
                    usuario = usuarioRestClient.procuraPorEmail(usuarioOffs[0].getEmail());
                } catch (Exception e) {
                    Log.i("ERRO CONSULTA SERVIDOR", e.getMessage());
                }

                if (usuario == null) {
                    return 0;
                } else {
                    return Math.toIntExact(usuario.getCodigo());
                }
            } else {
                return usuarioOff.getCodigo();
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    private class GetLocalUserByIdAsync extends AsyncTask<Integer, Void, UsuarioOff> {

        @Override
        protected UsuarioOff doInBackground(Integer... integers) {
            try {
                UsuarioOff usuarioOff = AppDatabase.getAppDatabase(context).usuarioDao().getUsuarioById(integers[0]);
                return usuarioOff;
            } catch (Exception e) {
                Log.i("ERRO NA CONSULTA", e.getMessage());
                return null;

            }

        }

        @Override
        protected void onPostExecute(UsuarioOff usuarioOff) {
            super.onPostExecute(usuarioOff);
        }
    }
}
