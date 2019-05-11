package com.example.franz.wallet.Model;

import com.orm.SugarRecord;



public class ModelUsuario extends SugarRecord<ModelUsuario>
{

    private String nick;
    private String password;
    private String nombre;
    private int activo;

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public ModelUsuario(String nick, String password, String nombre) {
        this.nick = nick;
        this.password = password;
        this.nombre = nombre;
    }

    public ModelUsuario() {
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
