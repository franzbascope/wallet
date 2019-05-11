package com.example.franz.wallet.Model;

import android.widget.Spinner;

import com.orm.SugarRecord;

public class ModelCategoria extends SugarRecord<ModelCategoria>
{
    private String descripcion;
    private int tipo;

    public ModelUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(ModelUsuario usuario) {
        this.usuario = usuario;
    }

    private ModelUsuario usuario;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    @Override
    public String toString() {
        return descripcion;
    }

}
