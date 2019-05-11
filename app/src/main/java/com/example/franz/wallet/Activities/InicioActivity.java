package com.example.franz.wallet.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;

import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private static final int DATA_CATEGORIA_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
               registrarMovimiento();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void desactivarUsuarios()
    {
        List<ModelUsuario> users = ModelUsuario.listAll(ModelUsuario.class);
        for (ModelUsuario user:users)
        {
            user.setActivo(0);
            user.save();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            desactivarUsuarios();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void registrarMovimiento()
    {
        Intent intent = new Intent(getApplicationContext(), InsertarMovimiento.class);
        startActivity(intent);
    }

    public void accionNuevaGestion(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Gestion.class);
        startActivity(intent);

    }

    public void accionNuevoMovimiento(View view)
    {
        registrarMovimiento();
    }

    public void accionNuevaCategoria(View view) {
        Intent intent = new Intent(this, abm_categoria_activity.class);
        startActivityForResult(intent, DATA_CATEGORIA_CODE);
    }

    public void accionMovimientos(View view) {

        Intent intent = new Intent(getApplicationContext(), List_Movimientos_Activity.class);
        startActivity(intent);
    }

    public void accionGestiones(View view) {
        Intent intent = new Intent(getApplicationContext(), DetalleGestionActivity.class);
        startActivity(intent);
    }
}
