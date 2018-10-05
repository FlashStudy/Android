package br.com.flashstudy.flashstudy_mobile.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

public class Util {

    public static boolean isEmailValido(String email) {
        return (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    public static long getLocalUserCodigo(Context context){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
            return sharedPreferences.getLong("codigo", 0);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("ERRO CODIGO LOCAL", e.getMessage());
        }
        return (long) 0;
    }

    public static long setLocalUserCodigo(Context context, long codigo){
        try {
            SharedPreferences preferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("codigo", codigo);
            editor.apply();

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("ERRO CODIGO LOCAL", e.getMessage());
        }
        return (long) 0;
    }
}
