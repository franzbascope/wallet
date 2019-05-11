package com.example.franz.wallet.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.franz.wallet.Librerias.DatePickerFragment;
import com.example.franz.wallet.Model.ModelGestion;
import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Gestion extends AppCompatActivity {

    private Date fechaCompra= new Date();
    private DateFormat formatoFecha = new SimpleDateFormat("EEE d MMM yy");
    private EditText txtDescripcion;
    private EditText txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    accionMotrarCalendario();
                } else {

                }
            }
        });
    }

    public void accionNuevaGestion(View view)
    {
        Calendar cal = Calendar.getInstance();
        long hora=0;
        fechaCompra.setTime(hora);
        String descripcion= txtDescripcion.getText().toString();
        String fecha = txtFecha.getText().toString();
        ModelUsuario usuario = ModelUsuario.find(ModelUsuario.class, "activo = ? ", "1").get(0);
        if(descripcion.isEmpty())
        {
            Toast.makeText(this,"Ingrese una Descripcion",Toast.LENGTH_SHORT);
            return;
        }
        if(fecha.isEmpty())
        {
            Toast.makeText(this,"Seleccione una Fecha",Toast.LENGTH_SHORT);
            return;
        }
        ModelGestion gestion = new ModelGestion();
        gestion.setActivo(1);
        gestion.setDescripcion(descripcion);
        gestion.setFecha_inicio(fechaCompra);
        gestion.setFecha_cierre(fechaCompra);
        gestion.setSaldo(0);
        gestion.setTotalIngresos(0);
        gestion.setTotalEgresos(0);
        gestion.setUsuario(usuario);


         gestion.save();

        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        startActivity(intent);




    }
    public void accionMotrarCalendario() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                fechaCompra = new Date(year, month, day);
                // String fecha=fechaCompra.getDay()+"/"+fechaCompra.getMonth()+"/"+fechaCompra.getYear();
                txtFecha.setText(formatoFecha.format(fechaCompra));

            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
