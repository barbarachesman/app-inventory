package br.ufop.barbara.inventory;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class ItemList extends ListActivity {
    private static final int ITEM_EDIT = 1;
    private static final int ITEM_CALL = 2;
    private ArrayList<Item> items = new ArrayList<Item>();
    private Integer action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = loadItems(this);
        Intent it = getIntent();
        Bundle params = it.getExtras();
        //action = (Integer) params.get("action");

        setListAdapter(new ItemAdapter(this, items));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Take item on a specific position
        Item item = (Item) this.getListAdapter().getItem(position);

        if(action == 0){
            //Enviar alunos e posicao para activity ALunoEdit
            Intent it = new Intent(this, ItemEdit.class);
            //it.putExtra("alunos", alunos);
            it.putExtra("action", action );
            it.putExtra("posicao", position);
            it.putExtra("item", (Parcelable) item );
            startActivityForResult(it, ITEM_EDIT);

        }
        else if(action == 1) {
           /* ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 1);

            //Enviar alunos e posicao para activity ALunoCall
            Uri uri = Uri.parse("tel:" + String.valueOf(item.phone));
            Intent it = new Intent(Intent.ACTION_CALL, uri);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(it);
            }*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if ( requestCode == ITEM_EDIT) {
            //if (resultCode == RESULT_OK) {
                items = loadItems(this); // data.getParcelableArrayListExtra("items");
                setListAdapter(new ItemAdapter(this, items));

            //}
        //}
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                //Permission has been denied
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onBackPressed(){
       items = loadItems(this);
        finish();
    }

    public static void saveItems(Context ctx, ArrayList<Item> items) {
        FileOutputStream file ;
        try{
            file = ctx.openFileOutput("t.tmp", Context.MODE_PRIVATE);
            ObjectOutputStream ois = new ObjectOutputStream(file);
            ois.writeObject(items);
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Item> loadItems(Context ctx){
        FileInputStream file ;
        try{
            file = ctx.openFileInput("t.tmp");
            ObjectInputStream ois = new ObjectInputStream(file);
            ArrayList<Item> items = (ArrayList<Item>) ois.readObject();
            ois.close();

            /*
            items.add(new Item(102456, "Cadeira", new Date(), "C304", "Ativo"));
            items.add(new Item(101783, "Mesa", new Date(), "C304", "Ativo"));
            items.add(new Item(103204, "Datashow", new Date(), "C304", "Ativo"));
            items.add(new Item(104334, "Quadro", new Date(), "C304", "Ativo"));
            items.add(new Item(293500, "Monitor", new Date(), "C304", "Ativo"));
            */

            return items;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
