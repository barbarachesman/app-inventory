package br.ufop.barbara.inventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemNovo extends AppCompatActivity {

    private ArrayList<Item> items;
    private int posicao = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_novo);

        EditText et3 = (EditText) findViewById(R.id.txtdata);
        et3.setText("01/01/2010");

        items = ItemList.loadItems(this);
    }



    public void confirmar(View view){
        try {

            EditText et1 = (EditText) findViewById(R.id.txtcode);
            int code = Integer.parseInt(et1.getText().toString());

            EditText et2 = (EditText) findViewById(R.id.txtdesc);
            String desc = et2.getText().toString();

            EditText et3 = (EditText) findViewById(R.id.txtdata);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dataS = et3.getText().toString();
            Date data = formatter.parse(dataS);

            EditText et4 = (EditText) findViewById(R.id.txtlocal);
            String local = et4.getText().toString();

            EditText et5 = (EditText) findViewById(R.id.txtstatus);
            String status = et5.getText().toString();

            Item item = new Item(code, desc, data, local, status);

            items.add(item);
            //Envia lista de alunos atualizada para a activity que a chamou
            Intent it = new Intent();
            //it.putExtra("alunos", alunos);
            setResult(RESULT_OK, it);
            ItemList.saveItems(this, items);

            showMessage("Cadastrado com sucesso", ItemNovo.this);
            finish();

        }
        catch (Exception e) {
            e.printStackTrace();
            showMessage("Erro ao cadastrar", ItemNovo.this);
        }

    }

    public void cancelar(View v){
       finish();
    }

    public void showMessage(String Caption, Activity activity) {
        Toast.makeText(this, Caption, Toast.LENGTH_SHORT).show();
        /*
        AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
        dialogo.setTitle("Atencao");
        dialogo.setMessage(Caption);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();*/
    }
}
