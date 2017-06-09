package br.ufop.barbara.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemEdit extends AppCompatActivity {

    private ArrayList<Item> items = new ArrayList<Item>();
    private int posicao = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadItems();
        //Receber dados de ItemList
        Intent it = getIntent();
        Bundle params = it.getExtras();

        //alunos = (ArrayList<Aluno>) params.get("alunos");
        posicao = (int) params.get("posicao");

        setContentView(R.layout.activity_item_edit);

        EditText et1 = (EditText) findViewById(R.id.txtcode);
        et1.setText("" + items.get(posicao).getCode());

        EditText et2 = (EditText) findViewById(R.id.txtdesc);
        et2.setText("" + items.get(posicao).getDescription());

        EditText et3 = (EditText) findViewById(R.id.txtdata);
        et3.setText("" + items.get(posicao).getDateInventory());

        EditText et4 = (EditText) findViewById(R.id.txtlocal);
        et4.setText("" + items.get(posicao).getLocation());

        EditText et5 = (EditText) findViewById(R.id.txtstatus);
        et5.setText("" + items.get(posicao).getStatus());
    }

    public void confirmar(View view) throws ParseException {
        EditText et1 = (EditText) findViewById(R.id.txtcode);
        int code =  Integer.parseInt(et1.getText().toString());

        EditText et2 = (EditText) findViewById(R.id.txtdesc);
        String desc = et2.getText().toString();

        EditText et3 = (EditText) findViewById(R.id.txtdata);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String dataS = et3.getText().toString();
        Date data = formatter.parse(dataS);

        EditText et4 = (EditText) findViewById(R.id.txtlocal);
        String local = et4.getText().toString();

        EditText et5 = (EditText) findViewById(R.id.txtstatus);
        String status =et5.getText().toString();


        Item item = new Item(code, desc, data, local, status);
        items.set(posicao, item);

        //Envia lista de alunos atualizada para a activity que a chamou
        saveItems();
        finish();

    }

    private void saveItems() {
        FileOutputStream file ;
        try{
            file = this.openFileOutput("t.tmp", Context.MODE_PRIVATE);
            ObjectOutputStream ois = new ObjectOutputStream(file);
            ois.writeObject(items);
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadItems(){
        FileInputStream file ;
        try{
            file = this.openFileInput("t.tmp");
            ObjectInputStream ois = new ObjectInputStream(file);
            items = (ArrayList<Item>) ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void deletar(View view){
        items.remove(posicao);

       // Intent it = new Intent();
        saveItems();
//        it.putExtra("alunos", alunos);
        //setResult(RESULT_OK, it);
        finish();
    }

    
}
