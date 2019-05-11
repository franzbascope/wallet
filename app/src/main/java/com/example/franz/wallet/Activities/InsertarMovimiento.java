package com.example.franz.wallet.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.franz.wallet.Librerias.AppUtils;
import com.example.franz.wallet.Librerias.DatePickerFragment;
import com.example.franz.wallet.Librerias.TimePickerFragment;
import com.example.franz.wallet.Librerias.TimePickerInterface;
import com.example.franz.wallet.Model.ModelCategoria;
import com.example.franz.wallet.Model.ModelGestion;
import com.example.franz.wallet.Model.ModelMovimientos;
import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class InsertarMovimiento extends AppCompatActivity implements TimePickerInterface {

    private static final int DATA_CATEGORIA_CODE = 1;
    private Date fechaCompra;
    private EditText txtDescripcion;
    private EditText txtValor;
    private EditText txtFecha;
    private EditText txtHora;
    private List<ModelCategoria> categorias = new LinkedList<>();
    private String[] tiposMovimiento = {"Egreso", "Ingreso"};
    private DateFormat formatoFecha = new SimpleDateFormat("EEE d MMM yy");
    private long mLastTimePickerValue;
    private Spinner spinnerCategorias;
    private Spinner tipoMovimiento;
    private Spinner spinnerGestion;
    List<ModelGestion> gestiones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_movimiento);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtValor = (EditText) findViewById(R.id.txtValor);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtHora = (EditText) findViewById(R.id.txtHora);
         spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategoria);
        spinnerGestion = (Spinner) findViewById(R.id.spinnerGestion);
         obtenerGestiones();
        obtenerCategorias("0");
        tipoMovimiento = (Spinner) findViewById(R.id.spinnerTipoMovimiento);
        tipoMovimiento.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tiposMovimiento));


        //Evento para controlar los tipos de categorias segun el tipo de movimiento
        tipoMovimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                String tipoMovimiento= String.valueOf(index);
                obtenerCategorias(tipoMovimiento);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Evento para poder mostrar el calendario
        txtFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    accionMotrarCalendario();
                } else {

                }
            }
        });

        //evento para mostrar la hora
        txtHora.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    accionMotrarHora();
                } else {

                }
            }
        });
    }

    public void obtenerGestiones()
    {
        ModelUsuario usuarioActual = ModelUsuario.find(ModelUsuario.class, "activo = ? ", "1").get(0);
        gestiones= ModelGestion.find(ModelGestion.class, "usuario = ? ", String.valueOf(usuarioActual.getId()));
        ArrayAdapter<ModelGestion> adapter =
                new ArrayAdapter<ModelGestion>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, gestiones);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinnerGestion.setAdapter(adapter);
    }

    public void obtenerCategorias(String tipo) {
        ModelUsuario usuario = ModelUsuario.find(ModelUsuario.class, "activo = ? ", "1").get(0);

        try{
            categorias = ModelCategoria.find(ModelCategoria.class, "tipo = ? and usuario= ?", tipo,String.valueOf(usuario.getId()) );
            if(categorias.isEmpty())
            {

            }
            //this.categorias.clear();
            ArrayAdapter<ModelCategoria> adapter =
                    new ArrayAdapter<ModelCategoria>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, categorias);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

            spinnerCategorias.setAdapter(adapter);
        } catch (Exception ex){
            String hola="Hola";
            //registrarCategorias();
        }


    }



    public void accionNuevaCategoria(View view)
    {
        Intent intent = new Intent(this, CategoriaActivity.class);
        startActivityForResult(intent, DATA_CATEGORIA_CODE);
    }

    public void accionRegistrarMovimiento(View view)
    {

        ModelGestion gestion = (ModelGestion) spinnerGestion.getSelectedItem();
        String descripcion = txtDescripcion.getText().toString();
        String fecha = txtFecha.getText().toString();
        String valor = txtValor.getText().toString();
        String hora = txtHora.getText().toString();
        ModelCategoria categoria = (ModelCategoria) spinnerCategorias.getSelectedItem();
        int tipo = tipoMovimiento.getSelectedItemPosition();
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
        if(hora.isEmpty())
        {
            Toast.makeText(this,"Ingrese la hora",Toast.LENGTH_SHORT);
            return;
        }
        if(valor.isEmpty())
        {
            Toast.makeText(this,"Ingrese la hora",Toast.LENGTH_SHORT);
            return;
        }


        ModelMovimientos mov= new ModelMovimientos();
        mov.setCategoria(categoria);
        mov.setFecha(fechaCompra);
        mov.setTipo(tipo);
        mov.setDescripcion(descripcion);
        mov.setValor(Float.parseFloat(valor));
        mov.setHora(mLastTimePickerValue);
        mov.setGestion(gestion);
        mov.save();
        List<ModelMovimientos> listmovs  = ModelMovimientos.find(ModelMovimientos.class, "tipo = ? ", "0");
        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == DATA_CATEGORIA_CODE) {
            String param= String.valueOf(tipoMovimiento.getSelectedItemPosition());
            obtenerCategorias(param);

        }

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
    public void accionMotrarHora() {
        TimePickerFragment timePicker = new TimePickerFragment();


        if (mLastTimePickerValue > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mLastTimePickerValue);
            Bundle bundle = new Bundle();
            bundle.putInt("hour", calendar.get(Calendar.HOUR_OF_DAY));
            bundle.putInt("minute", calendar.get(Calendar.MINUTE));
            timePicker.setArguments(bundle);
        }
        timePicker.delegate = InsertarMovimiento.this;
        timePicker.setCancelable(false);
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSelected(int hours, int minute) {
        txtHora.setText(String.valueOf(AppUtils.formatCharLength(2, hours) + ":" + AppUtils.formatCharLength(2, minute)));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        String formatted = format1.format(cal.getTime());
        mLastTimePickerValue = AppUtils.timeIntoTimeStamp(formatted + " " + hours + ":" + minute);

    }
}
