package com.example.franz.wallet.Model;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

public class ModelGestion  extends SugarRecord<ModelGestion>
{
    private String descripcion;
    private int activo;
    private float totalIngresos;
    private float totalEgresos;
    private float saldo;
    private Date fecha_inicio= new Date();
    private Date fecha_cierre = new Date();

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public float getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(float totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public float getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(float totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(Date fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public ModelUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(ModelUsuario usuario) {
        this.usuario = usuario;
    }

    ModelUsuario usuario;

    @Override
    public String toString() {
        return descripcion;
    }

}
