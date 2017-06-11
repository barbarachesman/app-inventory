package br.ufop.barbara.inventory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by barbara on 17/05/17.
 */

public class ItemAdapter extends BaseAdapter{


    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = items.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.smile_detail, parent, false);

        TextView textNome = (TextView) v.findViewById(R.id.codigo_detalhe);
        textNome.setText(String.valueOf(item.getCode()));

        TextView curso = (TextView) v.findViewById(R.id.desc);
        curso.setText(item.getDescription());

        TextView periodo = (TextView) v.findViewById(R.id.data);
        periodo.setText((String.valueOf(ItemEdit.DateToString(item.getDateInventory()))));

        TextView matricula = (TextView) v.findViewById(R.id.local);
        matricula.setText(String.valueOf(item.getLocation()));

        TextView coeficiente = (TextView) v.findViewById(R.id.status);
        coeficiente.setText(String.valueOf(item.getStatus()));


        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        long diff = ((new Date()).getTime() - item.getDateInventory().getTime())/1000/60/60/24;
        Log.e("adapter", "diferenca: "+diff);
        if(diff < 365) {
            img.setImageResource(R.drawable.check);
        } else {
            img.setImageResource(R.drawable.warning);
        }

        return v;
    }
}
