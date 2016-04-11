package com.gustavovalderrama.diccionario;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity {

    EditText txtPalabra;
    Spinner spnIdioma;
    ListView resultados;
    final ArrayList<String> lista = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtPalabra = (EditText) findViewById(R.id.editText);
        spnIdioma = (Spinner) findViewById(R.id.spinner);
        resultados = (ListView) findViewById(R.id.resultados);

        List list = new ArrayList();
        list.add("Inglés");
        list.add("Francés");
        list.add("Alemán");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnIdioma.setAdapter(arrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddWord.class);
                startActivity(intent);
            }
        });
    }

    public void buscarPalabra(View view)
    {
        resultados.setAdapter(null);
        ConfigDB configdb = new ConfigDB(this, "Diccionario", null, 1);

        SQLiteDatabase db = configdb.getWritableDatabase();
        if (!isEmpty(txtPalabra))
        {
            String palabra = txtPalabra.getText().toString();
            String temp = spnIdioma.getSelectedItem().toString();
            int idioma = chooseLang(temp);

            Cursor fila = db.rawQuery("SELECT * FROM palabras WHERE palabra LIKE '%" + palabra + "%' AND idioma = "+ idioma +";", null);
            try {
                if (fila != null)
                {
                    int j = fila.getColumnIndexOrThrow("palabra");
                    int i = fila.getColumnIndexOrThrow("traduccion");

                    Palabra palabras[] = new Palabra[fila.getCount()];
                    int contador = 0;
                    while(fila.moveToNext())
                    {
                        palabra = fila.getString(j);
                        String traduccion = fila.getString(i);
                        palabras[contador] = new Palabra(palabra, traduccion);
                        contador++;
                    }
                    PalabraArrayAdapter adapter = new PalabraArrayAdapter(this, R.layout.listview_item_row, palabras);
                    resultados.setAdapter(adapter);
                }
                else
                {
                    Snackbar.make(view, "No existen resultados para su búsqueda", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            } catch (Exception ex)
            {
                Log.i("bdpalabra", "Error al abrir o crear la base de datos " + ex);
            }
        }
        else
        {
            Snackbar.make(view, "¡No has ingresado ningúna palabra!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    public boolean isEmpty(EditText text)
    {
        if(text.getText().toString().trim().length() > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private int chooseLang(String spn)
    {
        int lang = 0;
        switch (spn)
        {
            case "Inglés":
                lang = 1;
                break;
            case "Francés":
                lang = 2;
                break;
            case "Alemán":
                lang = 3;
                break;
            default:
                lang = 0;
                break;
        }
        return lang;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
