package com.example.franz.wallet.Model;

import com.orm.SugarRecord;

import java.util.Date;

public class ModelMovimientos extends SugarRecord<ModelMovimientos>
{
    private ModelCategoria categoria;
    private String descripcion;
    private int tipo;
    private float valor;
    private Date fecha;
    private long hora;
    private ModelGestion gestion;

    public ModelGestion getGestion() {
        return gestion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setGestion(ModelGestion gestion) {
        this.gestion = gestion;
    }

    public long getHora() {
        return hora;
    }

    public void setHora(long hora) {
        this.hora = hora;
    }



    public ModelCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ModelCategoria categoria) {
        this.categoria = categoria;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
