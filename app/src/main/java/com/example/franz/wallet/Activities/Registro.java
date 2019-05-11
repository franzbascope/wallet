package com.example.franz.wallet.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;

import java.util.List;

public class Registro extends AppCompatActivity {

    private EditText txtNick;
    private EditText txtNombre;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtNick = (EditText) findViewById(R.id.txtNick);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    public void registerarAccion(View view)
    {

        String nick=txtNick.getText().toString();
        String nombre=txtNombre.getText().toString();
        String senha=txtPassword.getText().toString();
        if(nick.isEmpty())
        {
            Toast.makeText(this, "Ingrese un Nick", Toast.LENGTH_SHORT);
            return;
        }
        if(nombre.isEmpty())
        {
            Toast.makeText(this, "Ingrese un Nombre", Toast.LENGTH_SHORT);
            return;
        }
        if(senha.isEmpty())
        {
            Toast.makeText(this, "Ingrese una Contraseña", Toast.LENGTH_SHORT);
            return;
        }
        if(ModelUsuario.find(ModelUsuario.class, "nick = ? ", nick)!=null)
        {
            List<ModelUsuario> users = ModelUsuario.find(ModelUsuario.class, "nick = ? ", nick);
            if(!users.isEmpty())
            {
                Toast.makeText(this, "Este nick ya existe Ingrese otro", Toast.LENGTH_SHORT);
                return;

            }
        }

        ModelUsuario user = new ModelUsuario();
        user.setNick(nick);
        user.setNombre(nombre);
        user.setPassword(senha);
        user.save();

        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);




    }
}
