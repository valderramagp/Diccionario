package com.gustavovalderrama.diccionario;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gusta on 07/04/2016.
 */
public class AddWord extends AppCompatActivity
{
    EditText txtWord, txtMeaning;
    RadioGroup rdLang;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newword);

        txtWord = (EditText) findViewById(R.id.txtWord);
        txtMeaning = (EditText) findViewById(R.id.txtMeaning);
        rdLang = (RadioGroup) findViewById(R.id.rdIdioma);
    }

    public void insertarPalabra(View view)
    {
        ConfigDB configDB = new ConfigDB(this, "Diccionario", null, 1);
        SQLiteDatabase db = configDB.getWritableDatabase();
        String word = txtWord.getText().toString();
        String meaning = txtMeaning.getText().toString();

        int id = rdLang.getCheckedRadioButtonId();
        id = getSelectedLang(id);

        if ((!word.isEmpty()) && (!meaning.isEmpty()) && (id != 0))
        {
            ContentValues row = new ContentValues();
            row.put("palabra", word);
            row.put("traduccion", meaning);
            row.put("idioma", id);

            long temp = db.insert("palabras", null, row);
            db.close();
            if (temp != -1 )
            {
                Snackbar.make(view, "¡Se ha agregado la palabra correctamente!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            else
            {
                Snackbar.make(view, "¡Ha ocurrido un error!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }
        else {
            if(word.isEmpty())
            {
                Snackbar.make(view, "¡No has ingresado ningúna palabra!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            if (meaning.isEmpty())
            {
                Snackbar.make(view, "¡No has ingresado la traducción de la palabra!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            if (id == 0)
            {
                Snackbar.make(view, "¡No has seleccionado ningún idioma!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(this.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        limpiar();
    }

    private int getSelectedLang(int rb)
    {
        int lang = 0;
        switch (rb)
        {
            case -1:
                lang = 0;
                break;
            case R.id.radioButton:
                lang = 1;
                break;
            case R.id.radioButton2:
                lang = 2;
                break;
            case R.id.radioButton3:
                lang = 3;
                break;
            default:
                lang = 0;
                break;
        }
        return lang;
    }

    private boolean isEmpty(String text)
    {
        if(text.trim().length() > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private void limpiar()
    {
        RadioButton rb = (RadioButton) findViewById(R.id.radioButton);
        rb.setChecked(true);
        txtWord.setText("");
        txtMeaning.setText("");
    }
}
