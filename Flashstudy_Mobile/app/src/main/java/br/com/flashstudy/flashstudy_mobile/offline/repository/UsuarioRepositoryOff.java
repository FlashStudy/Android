package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;

public class UsuarioRepositoryOff {

    public UsuarioOff getLocaluserById(long codigo, Context context) {
        try {
            return AppDatabase.getAppDatabase(context).usuarioDao().getUsuarioById(codigo);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean salvar(UsuarioOff usuario, Context context) {
        try {
            AppDatabase.getAppDatabase(context).usuarioDao().insert(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long login(UsuarioOff usuarioOff, Context context) {
        try {
            UsuarioOff usuarioOff1;
            try {
                usuarioOff1 = AppDatabase.getAppDatabase(context).usuarioDao().getUsuarioByEmail(usuarioOff.getEmail());
            } catch (Exception e) {
                Log.i("ERRO NA CONSULTA", e.getMessage());
                return (long) 0;
            }

            if (usuarioOff1 != null) {
                if (usuarioOff.getSenha().equals(usuarioOff.getSenha())) {
                    return usuarioOff.getCodigo();
                } else {
                    return (long) 0;
                }
            }
        } catch (Exception e) {
            Log.i("ERRO NO LOGIN:", e.getMessage());
            return 0;
        }
        return 0;
    }
}
