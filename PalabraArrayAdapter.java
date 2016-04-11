package com.gustavovalderrama.diccionario;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gusta on 07/04/2016.
 */
public class PalabraArrayAdapter extends ArrayAdapter<Palabra>
{
    Context myContext;
    int myLayoutResourceID;
    Palabra data[] = null;

    public PalabraArrayAdapter(Context context, int myLayoutResourceID, Palabra[] data)
    {
        super(context, myLayoutResourceID, data);
        this.myContext = context;
        this.myLayoutResourceID = myLayoutResourceID;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        PalabraHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)myContext).getLayoutInflater();
            row = inflater.inflate(myLayoutResourceID, parent, false);

            holder = new PalabraHolder();
            holder.textView = (TextView) row.findViewById(R.id.palabra);
            holder.textView2 = (TextView) row.findViewById(R.id.significado);

            row.setTag(holder);
        }
        else
        {
            holder = (PalabraHolder) row.getTag();
        }

        Palabra palabras = data[position];

        holder.textView.setText(palabras.getPalabra());
        holder.textView2.setText(palabras.getTraduccion());

        return row;
    }

    static class PalabraHolder {
        TextView textView, textView2;
    }
}