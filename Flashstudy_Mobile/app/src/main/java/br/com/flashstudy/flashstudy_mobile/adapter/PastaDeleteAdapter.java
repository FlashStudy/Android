package br.com.flashstudy.flashstudy_mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;

public class PastaDeleteAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private List<PastaOff> pastaOffs;
    private List<Boolean> selecionados = new ArrayList<>();

    public PastaDeleteAdapter(Activity a, Context context, List<PastaOff> pastaOffs) {
        activity = a;
        mContext = context;
        this.pastaOffs = pastaOffs;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return pastaOffs.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return pastaOffs.get(arg0);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return pastaOffs.get(position).getCodigo();
    }

    public boolean isSelecionado(int position){
        return selecionados.get(position);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        selecionados.add(false);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.checkbox_texto_layout, parent, false);

        TextView nome = (TextView) vi.findViewById(R.id.txtTitle); // title
        CheckBox checkBox = vi.findViewById(R.id.checkbox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selecionados.add(position, isChecked);
            }
        });

        if (pastaOffs.get(position).getNome().equals("Sem categoria")){
            checkBox.setEnabled(false);
        }

        nome.setText(pastaOffs.get(position).getNome());
        return vi;
    }
}
