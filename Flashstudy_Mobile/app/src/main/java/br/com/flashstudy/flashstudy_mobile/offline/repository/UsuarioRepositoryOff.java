package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;

public class UsuarioRepositoryOff {
    private Context context;

    public UsuarioOff getLocaluserById(long codigo, Context context) {
        try {
            this.context = context;
            return new GetLocalUserByIdAsync().execute(codigo).get();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean salvar(UsuarioOff usuario, Context context) {
        try {
            this.context = context;
            return new AdicionaUsuarioAsync().execute(usuario).get();
        } catch (Exception e) {
            return false;
        }
    }

    public long login(UsuarioOff usuarioOff, Context context1) {
        try {
            this.context = context1;
            return new LoginUsuarioAsync().execute(usuarioOff).get();
        } catch (Exception e) {
            Log.i("ERRO NO LOGIN:", e.getMessage());
            return 0;
        }
    }

    private class AdicionaUsuarioAsync extends AsyncTask<UsuarioOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(UsuarioOff... usuarioOffs) {
            try {
                AppDatabase.getAppDatabase(context).usuarioDao().insert(usuarioOffs[0]);
                return true;
            } catch (Exception e) {
                Log.i("ERRO CADASTRO USUÁRIO", e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    private class LoginUsuarioAsync extends AsyncTask<UsuarioOff, Void, Long> {

        @Override
        protected Long doInBackground(UsuarioOff... usuarioOffs) {

            UsuarioOff usuarioOff;
            try {
                usuarioOff = AppDatabase.getAppDatabase(context).usuarioDao().getUsuarioByEmail(usuarioOffs[0].getEmail());
            } catch (Exception e) {
                Log.i("ERRO NO LOGIN", e.getMessage());
                return (long) 0;
            }

            if (usuarioOff != null) {
                if (usuarioOffs[0].getSenha().equals(usuarioOff.getSenha())) {
                    return usuarioOff.getCodigo();
                } else {
                    return (long) 0;
                }
            }
            return (long) 0;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }

    private class GetLocalUserByIdAsync extends AsyncTask<Long, Void, UsuarioOff> {

        @Override
        protected UsuarioOff doInBackground(Long... longs) {
            try {
                UsuarioOff usuarioOff = AppDatabase.getAppDatabase(context).usuarioDao().getUsuarioById(longs[0]);
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
