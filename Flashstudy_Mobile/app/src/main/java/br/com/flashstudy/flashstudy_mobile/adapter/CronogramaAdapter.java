package br.com.flashstudy.flashstudy_mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;

public class CronogramaAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private List<DisciplinaOff> disciplinaOffs;

    public CronogramaAdapter(Activity a, Context context, List<DisciplinaOff> disciplinaOffs) {
        activity = a;
        mContext = context;
        this.disciplinaOffs = disciplinaOffs;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return disciplinaOffs.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.icone_texto_layout, parent, false);

        TextView nome = vi.findViewById(R.id.txtTitle); // title
        ImageView icone = vi.findViewById(R.id.imgIcon); // thumb image

        nome.setText(disciplinaOffs.get(position).getNome());
        icone.setImageResource(R.drawable.ic_materia);
        return vi;
    }
}
