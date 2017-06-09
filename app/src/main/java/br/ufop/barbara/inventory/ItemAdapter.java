package br.ufop.barbara.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
        View v = inflater.inflate(R.layout.smile_detail, null);

        TextView textNome = (TextView) v.findViewById(R.id.code);
        textNome.setText(item.getCode());

        TextView curso = (TextView) v.findViewById(R.id.desc);
        curso.setText(item.getDescription());

        TextView periodo = (TextView) v.findViewById(R.id.data);
        periodo.setText((String.valueOf(item.getDateInventory())));

        TextView matricula = (TextView) v.findViewById(R.id.local);
        matricula.setText(String.valueOf(item.getLocation()));

        TextView coeficiente = (TextView) v.findViewById(R.id.status);
        coeficiente.setText(String.valueOf(item.getStatus()));


        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        img.setImageResource(item.getImage());


        return v;
    }
}
