package com.example.franz.wallet.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.franz.wallet.Model.ModelCategoria;
import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;
import java.util.logging.Logger;

public class CategoriaActivity extends AppCompatActivity {
    private EditText txtDescripcion;
    private Spinner tipoMovimiento;
    private String[] tiposMovimiento = {"Egreso", "Ingreso"};
    public static final String PARAM_ID= "PARAM_ID";
    private int categoriaId=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        tipoMovimiento = (Spinner) findViewById(R.id.spinnerTipoMovimiento);
        tipoMovimiento.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tiposMovimiento));
        Intent parentIntent = getIntent();
        try {
            categoriaId= Integer.parseInt(parentIntent.getStringExtra(PARAM_ID));
            editarCategoria(categoriaId);
        }catch (Exception ex)
        {
            Log.e("Franz", "error al editar  : "+ex);
        }
        


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void editarCategoria(int categoriaId) {
        try {
            List<ModelCategoria> books = ModelCategoria.listAll(ModelCategoria.class);
            ModelCategoria ctg = ModelCategoria.find(ModelCategoria.class, "id = ? ", String.valueOf(categoriaId)).get(0);
            txtDescripcion.setText(ctg.getDescripcion());
            tipoMovimiento.setSelection(ctg.getTipo());

        }catch (Exception ex)
        {
            Log.e("Franz", "error al editar  : "+ex);
        }

    }

    public void accionNuevaCategoriaa(View view)
    {
        String descripcion = txtDescripcion.getText().toString();
        if(descripcion.isEmpty())
        {
            Toast.makeText(this,"Ingrese una Descripcion",Toast.LENGTH_SHORT);
            return;
        }
        ModelUsuario usuario = ModelUsuario.find(ModelUsuario.class, "activo = ? ", "1").get(0);
        if(categoriaId <0)
        {
            ModelCategoria categ = new ModelCategoria();
            categ.setDescripcion(descripcion);
            categ.setTipo(tipoMovimiento.getSelectedItemPosition());
            categ.setUsuario(usuario);
            categ.save();
        }
        else
            {
                ModelCategoria categ = ModelCategoria.findById(ModelCategoria.class, (long) categoriaId);
                categ.setDescripcion(descripcion);
                categ.setTipo(tipoMovimiento.getSelectedItemPosition());
                categ.setUsuario(usuario);
                categ.save();
            }

        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
