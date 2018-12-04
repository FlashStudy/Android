package br.com.flashstudy.flashstudy_mobile.Util;

import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class ConversaoDeClasse {
    public static UsuarioOff usuarioToUsuarioOff(Usuario usuario) {
        return new UsuarioOff(usuario.getCodigo(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
    }
}
