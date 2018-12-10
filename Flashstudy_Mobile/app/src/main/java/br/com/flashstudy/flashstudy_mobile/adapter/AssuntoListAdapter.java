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
import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.AssuntoRepositoryOff;

public class AssuntoListAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private List<AssuntoOff> assuntoOffList;

    AssuntoRepositoryOff assuntoRepositoryOff;

    public AssuntoListAdapter(Activity a, Context context, List<AssuntoOff> assuntoOffList) {
        activity = a;
        mContext = context;
        this.assuntoOffList = assuntoOffList;
        assuntoRepositoryOff = new AssuntoRepositoryOff(mContext);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return assuntoOffList.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.icone_texto_layout, parent, false);

        TextView nome = vi.findViewById(R.id.txtTitle); // title
        ImageView icone = vi.findViewById(R.id.imgIcon); // thumb image

        nome.setText(assuntoOffList.get(position).getTema());
        icone.setImageResource(R.drawable.ic_right_arrow);
        return vi;
    }
}
