package br.ufop.barbara.inventory;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int READ_CODE = 1;
    private static final int ITEM_EDIT = 1;
    private static final int ITEM_NOVO = 2;
    private Integer action = 0;


    public ArrayList<Item> items = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*//Date handling
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String dateInString = "24/05/2017";
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Fill list with some data
        items.add(new Item(102456, "Cadeira", date, "C304", "Ativo"));
        items.add(new Item(101783, "Mesa", date, "C304", "Ativo"));
        items.add(new Item(103204, "Datashow", date, "C304", "Ativo"));
        items.add(new Item(104334, "Quadro", date, "C304", "Ativo"));
        items.add(new Item(293500, "Monitor", date, "C304", "Ativo"));
        */

        setContentView(R.layout.activity_main);
    }

    public void add(View view){
        //Add - Chama nova activity para cadastrar aluno
        Intent it = new Intent(this, ItemNovo.class);

        startActivity(it);

    }

    public void edit(View view){
        //List - Lista (e edita) items conforme já implementado
        Intent it = new Intent(this, ItemList.class);
        it.putExtra( "items", items);
        action = 0;
        it.putExtra("action", action );
        startActivityForResult(it, ITEM_EDIT);

    }

    public void scanCode(View view) {
        try {
            //Start barcode scanner
            Intent it = new Intent("com.google.zxing.client.android.SCAN");
            startActivityForResult(it, READ_CODE);
        } catch(ActivityNotFoundException e) {
            //Barcode scanner is not installed
            Toast.makeText(this, "Please install Barcode Scanner from Play Store.", Toast.LENGTH_SHORT).show();

            //Optional: direct the user to Barcode Scanner's page on Play Store
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.zxing.client.android")));
            } catch (ActivityNotFoundException e2) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +
                        "com.google.zxing.client.android")));
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == READ_CODE || requestCode == ITEM_EDIT || requestCode == ITEM_NOVO) {
            if(resultCode == Activity.RESULT_OK) {
                items = data.getParcelableArrayListExtra("items");
                //Code scanning worked
                String result = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(this, "Result: " + result + "\nFormat: " + format, Toast.LENGTH_SHORT).show();

                //Change record
                for(int i = 0; i < items.size(); ++i) {
                    if(items.get(i).getCode() == Integer.parseInt(result)) {
                        //Update date inventory
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
                        Date date = new Date();
                        items.get(i).setDateInventory(date);
                        //Update location.. (later)

                        //Show changed record
                        Toast.makeText(this, "Item: " + items.get(i).getCode() + " inventoried at " +
                                dateFormat.format(items.get(i).getDateInventory()), Toast.LENGTH_SHORT).show();
                    }
                }
            } else if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Couldn't read code.", Toast.LENGTH_SHORT).show();
            }
        }
    }


   
}
