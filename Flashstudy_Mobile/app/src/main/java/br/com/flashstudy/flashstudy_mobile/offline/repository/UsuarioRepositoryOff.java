package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;

public class UsuarioRepositoryOff {

    private Context context;

    public UsuarioRepositoryOff(Context context) {
        this.context = context;
    }

    public UsuarioOff getLocaluserById(long codigo) {
        try {
            return new GetById().execute(codigo).get();
        } catch (Exception e) {
            Log.e("ERRO BUSCAR ID", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean salvar(UsuarioOff usuarioOff) {
        try {
            return new Salvar().execute(usuarioOff).get();
        } catch (Exception e) {
            Log.e("ERRO SALVAR USUARIO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(UsuarioOff usuarioOff) {
        try {
            return new Atualizar().execute(usuarioOff).get();
        } catch (Exception e) {
            Log.e("ERRO ATUALIZAR USUARIO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public long login(UsuarioOff usuarioOff) {
        try {
            UsuarioOff usuarioOff1;
            try {
                usuarioOff1 = new GetByEmail().execute(usuarioOff.getEmail()).get();
            } catch (Exception e) {
                Log.e("ERRO NA CONSULTA", e.getMessage());
                e.printStackTrace();
                return (long) 0;
            }

            if (usuarioOff1 != null) {
                if (usuarioOff1.getSenha().equals(usuarioOff.getSenha())) {
                    return usuarioOff1.getCodigo();
                } else {
                    return (long) 0;
                }
            }
        } catch (Exception e) {
            Log.e("ERRO NO LOGIN:", e.getMessage());
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    private class GetById extends AsyncTask<Long, Void, UsuarioOff> {

        @Override
        protected UsuarioOff doInBackground(Long... longs) {
            try {
                return AppDatabase.getAppDatabase(context).usuarioDao().getUsuarioById(longs[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC BUSCAR ID", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private class Salvar extends AsyncTask<UsuarioOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(UsuarioOff... usuarioOffs) {
            try {
                AppDatabase.getAppDatabase(context).usuarioDao().salvar(usuarioOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Atualizar extends AsyncTask<UsuarioOff, Void, Boolean> {

        @Override
        protected Boolean doInBackground(UsuarioOff... usuarioOffs) {
            try {
                AppDatabase.getAppDatabase(context).usuarioDao().atualizar(usuarioOffs[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO SALVAR ASYNC", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class GetByEmail extends AsyncTask<String, Void, UsuarioOff> {

        @Override
        protected UsuarioOff doInBackground(String... strings) {
            try {
                return AppDatabase.getAppDatabase(context).usuarioDao().getUsuarioByEmail(strings[0]);
            } catch (Exception e) {
                Log.e("ERRO GET EMAIL ASYNC", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}
