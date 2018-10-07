package br.com.flashstudy.flashstudy_mobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;

public class CronogramaListAdapter extends ArrayAdapter<Disciplina> {

    private List<Disciplina> disciplinas;
    private Context context;

    public CronogramaListAdapter(@NonNull Context context, List<Disciplina> disciplinas) {
        super(context, R.layout.activity_cronograma, disciplinas);
        this.disciplinas = disciplinas;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.one_text_layout, parent, false);

        Disciplina disciplina = disciplinas.get(position);

        TextView textViewNome = view.findViewById(R.id.textViewAtt1);
        textViewNome.setText(disciplina.getNome());

        return view;

    }
}
