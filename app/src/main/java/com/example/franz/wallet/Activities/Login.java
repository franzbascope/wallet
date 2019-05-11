package com.example.franz.wallet.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.franz.wallet.Model.ModelCategoria;
import com.example.franz.wallet.Model.ModelGestion;
import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;

import java.util.List;

public class Login extends AppCompatActivity {

    private EditText txtNick;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtNick = (EditText) findViewById(R.id.txtNick);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    public void loginAccion(View view) {


        String nick = txtNick.getText().toString();
        String senha = txtPassword.getText().toString();
        if (nick.isEmpty()) {
            Toast.makeText(this, "Ingrese un Nick", Toast.LENGTH_SHORT);
            return;
        }
        if (senha.isEmpty()) {
            Toast.makeText(this, "Ingrese una Contraseña", Toast.LENGTH_SHORT);
            return;
        }

        List<ModelUsuario> users;
        if (ModelUsuario.find(ModelUsuario.class, "nick = ? and password = ?", nick, senha) != null) {
            users = ModelUsuario.find(ModelUsuario.class, "nick = ? and password = ?", nick, senha);
            if (!users.isEmpty()) {
                desactivarUsuarios();
                ModelUsuario user = users.get(0);
                user.setActivo(1);
                user.save();
                if ( ModelCategoria.find(ModelCategoria.class, "usuario= ?", String.valueOf(user.getId())).isEmpty()) {
                    registrarCategorias();
                }
                List<ModelGestion> gestiones = ModelGestion.find(ModelGestion.class, "usuario = ? ", String.valueOf(user.getId()));
                if (gestiones.isEmpty()) {
                    Toast.makeText(this, "Ingresa una Gestion Primero", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getApplicationContext(), Gestion.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(this, "Este usuario no existe", Toast.LENGTH_SHORT);
                return;

            }
        } else {
            Toast.makeText(this, "Este usuario no existe", Toast.LENGTH_SHORT);
            return;

        }


    }

    private void registrarCategorias() {
        ModelUsuario usuario = ModelUsuario.find(ModelUsuario.class, "activo = ? ", "1").get(0);
        //Categorias Egresos
        ModelCategoria aux = new ModelCategoria();
        aux.setDescripcion("Comida");
        aux.setTipo(0);
        aux.setUsuario(usuario);
        aux.save();
        ModelCategoria aux1 = new ModelCategoria();
        aux1.setDescripcion("Transporte Publico");
        aux1.setTipo(0);
        aux1.setUsuario(usuario);
        aux1.save();
        ModelCategoria aux2 = new ModelCategoria();
        aux2.setDescripcion("Gasolina");
        aux2.setTipo(0);
        aux2.setUsuario(usuario);
        aux2.save();
        ModelCategoria aux3 = new ModelCategoria();
        aux3.setDescripcion("Gimnasio");
        aux3.setTipo(0);
        aux3.setUsuario(usuario);
        aux3.save();
        ModelCategoria aux4 = new ModelCategoria();
        aux4.setDescripcion("Ropa");
        aux4.setTipo(0);
        aux4.setUsuario(usuario);
        aux4.save();

        //Categorias Ingresos
        ModelCategoria aux5 = new ModelCategoria();
        aux5.setDescripcion("Sueldo");
        aux5.setTipo(1);
        aux5.setUsuario(usuario);
        aux5.save();
        ModelCategoria aux6 = new ModelCategoria();
        aux6.setDescripcion("Ventas");
        aux6.setTipo(1);
        aux6.setUsuario(usuario);
        aux6.save();
        ModelCategoria aux7 = new ModelCategoria();
        aux7.setDescripcion("Mesada");
        aux7.setTipo(1);
        aux7.setUsuario(usuario);
        aux7.save();

        ModelCategoria aux8 = new ModelCategoria();
        aux8.setDescripcion("Apuestas");
        aux8.setTipo(1);
        aux8.setUsuario(usuario);
        aux8.save();
        ModelCategoria aux9 = new ModelCategoria();
        aux9.setDescripcion("Otros");
        aux9.setTipo(1);
        aux9.setUsuario(usuario);
        aux9.save();

    }

    public void desactivarUsuarios() {
        List<ModelUsuario> users = ModelUsuario.listAll(ModelUsuario.class);
        for (ModelUsuario user : users) {
            user.setActivo(0);
            user.save();
        }

    }

    public void registroAccion(View view) {
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
    }
}
