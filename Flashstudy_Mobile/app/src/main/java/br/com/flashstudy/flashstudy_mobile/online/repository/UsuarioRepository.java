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
            return usuarioRestClient.cadastro(usuario);
        } catch (Exception e) {
            return null;
        }
    }

    public UsuarioOff login(Usuario usuario) {
        try {
            Usuario usuario1 = new Usuario();
            try {
                usuario1 = usuarioRestClient.procuraPorEmail(usuario.getEmail());
            } catch (Exception e) {
                Log.i("ERRO CONSULTA SERVIDOR", e.getMessage());
            }

            if (usuario1 == null) {
                return null;
            } else {
                return new UsuarioOff((int) (long) usuario1.getCodigo(),
                        usuario1.getNome(), usuario1.getEmail(), usuario1.getSenha());
            }
        } catch (Exception e) {
            Log.i("ERRO NO LOGIN:", e.getMessage());
            return null;
        }
    }
}