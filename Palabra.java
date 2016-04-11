package com.gustavovalderrama.diccionario;

/**
 * Created by gusta on 07/04/2016.
 */
public class Palabra {
    private String palabra, traduccion;

    public Palabra()
    {
        super();
    }

    public Palabra(String palabra, String traduccion)
    {
        this.palabra  = palabra;
        this.traduccion = traduccion;
    }

    public String getTraduccion() {
        return traduccion;
    }

    public void setTraduccion(String traduccion) {
        this.traduccion = traduccion;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
}
