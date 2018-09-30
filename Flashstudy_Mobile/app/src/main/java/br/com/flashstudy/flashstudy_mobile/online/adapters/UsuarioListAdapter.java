package br.com.flashstudy.flashstudy_mobile.online.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import br.com.flashstudy.flashstudy_mobile.online.model.Usuario;

public class UsuarioListAdapter extends ArrayAdapter<Usuario> {
    public UsuarioListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

   /* private List<UsuarioOff> usuarios;
    private Context context;

    public UsuarioListAdapter(@NonNull Context context, List<UsuarioOff> usuarios) {
        super(context, R.layout.usuario_layout, usuarios);
        this.usuarios = usuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.usuario_layout, parent, false);

        UsuarioOff usuario = this.usuarios.get(position);

        TextView textViewNome = view.findViewById(R.id.textViewName);
        textViewNome.setText(usuario.getNome());

        TextView textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewEmail.setText(usuario.getEmail());

        return view;

    }*/
}
