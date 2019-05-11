package com.example.franz.wallet.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.franz.wallet.Adapters.MovimientosListAdapter;
import com.example.franz.wallet.Model.ModelCategoria;
import com.example.franz.wallet.Model.ModelGestion;
import com.example.franz.wallet.Model.ModelMovimientos;
import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;

import java.util.List;

public class List_Movimientos_Activity extends AppCompatActivity {

    private ListView ViewMovs;
    private List<ModelMovimientos> movimientos;
    private List<ModelGestion> gestiones;
    private String[] tiposMovimiento = {"Egreso", "Ingreso", "Todos"};
    private Spinner spinnerTipoMovimiento;
    private Spinner spinnerGestion;
    private String tipoMovimiento = "2";
    private ModelUsuario usuarioActual = ModelUsuario.find(ModelUsuario.class, "activo = ?  ", "1").get(0);
    private ModelGestion gestionActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__movimientos_);
        if (ModelGestion.find(ModelGestion.class, "usuario = ?  ", String.valueOf(usuarioActual.getId())) != null && !ModelGestion.find(ModelGestion.class, "usuario = ?  ", String.valueOf(usuarioActual.getId())).isEmpty()) {
            gestionActual = ModelGestion.find(ModelGestion.class, "usuario = ?  ", String.valueOf(usuarioActual.getId())).get(0);
        }
        ViewMovs = (ListView) findViewById(R.id.list_movimientos);
        spinnerTipoMovimiento = (Spinner) findViewById(R.id.spinnerTipoMovimiento);
        spinnerGestion = (Spinner) findViewById(R.id.spinnerGestion);
        spinnerTipoMovimiento.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tiposMovimiento));
        cargarGestiones();
        registerForContextMenu(ViewMovs);
        mostrarMensajes();

        //Evento para controlar los tipos de categorias segun el tipo de movimiento
        spinnerGestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                gestionActual = (ModelGestion) spinnerGestion.getSelectedItem();
                mostrarMensajes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTipoMovimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                tipoMovimiento = String.valueOf(index);
                mostrarMensajes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void mostrarMensajes() {
        if(gestionActual!=null)
        {
            if (tipoMovimiento.equals("2")) {
                movimientos = ModelMovimientos.find(ModelMovimientos.class, "gestion = ? ", String.valueOf(gestionActual.getId()));
            } else {
                movimientos = ModelMovimientos.find(ModelMovimientos.class, "tipo = ? and gestion= ? ", tipoMovimiento, String.valueOf(gestionActual.getId()));
            }
            MovimientosListAdapter adapter = new MovimientosListAdapter(this, movimientos);
            ViewMovs.setAdapter(adapter);
        }
    }

    public void cargarGestiones() {

        ModelUsuario usuarioActual = ModelUsuario.find(ModelUsuario.class, "activo = ? ", "1").get(0);
        gestiones = ModelGestion.find(ModelGestion.class, "usuario = ? ", String.valueOf(usuarioActual.getId()));
        ArrayAdapter<ModelGestion> adapter =
                new ArrayAdapter<ModelGestion>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, gestiones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGestion.setAdapter(adapter);
    }
}
