package com.example.franz.wallet.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franz.wallet.Model.ModelGestion;
import com.example.franz.wallet.Model.ModelMovimientos;
import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.orm.SugarRecord.find;

public class DetalleGestionActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart mChart;
    private PieChart pieChartEgresos;
    private PieChart pieChartIngresos;
    private TextView txtIngresos;
    private TextView txtEgresos;
    private TextView txtSaldo;
    private Spinner spinnerGestion;
    private Spinner spinnerGraficos;
    // private String[] tiposGraficos = {"Totales","Grafico Combinado", "Grafico Torta de Ingresos", "Grafico Torta de Egresos","Grafico Lineas Ingresos","Grafico de Lineas Egresos"};
    private String[] tiposGraficos = {"Ingresos", "Egresos"};
    private List<ModelGestion> listaGestiones;
    private ModelGestion gestionActual;
    private ModelUsuario usuarioActual;
    private ArrayList<String> chartXvalores;
    private ArrayList<Entry> chartYvalores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gestion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mChart = (LineChart) findViewById(R.id.linechart);

        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        try
        {
            usuarioActual = find(ModelUsuario.class, "activo = ? ", "1").get(0);
            // add data
           /* setDataChart();

            // get the legend (only possible after setting data)
            Legend l = mChart.getLegend();

            // modify the legend ...
            // l.setPosition(LegendPosition.LEFT_OF_CHART);
            l.setForm(Legend.LegendForm.LINE);

            // no description text
            //mChart.setDescription("Demo Line Chart");
            //  mChart.setNoDataTextDescription("You need to provide data for the chart.");

            // enable touch gestures
            mChart.setTouchEnabled(true);

            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
            // mChart.setScaleXEnabled(true);
            // mChart.setScaleYEnabled(true);

            LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
            upper_limit.setLineWidth(4f);
            upper_limit.enableDashedLine(10f, 10f, 0f);
            upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            upper_limit.setTextSize(10f);

            LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
            lower_limit.setLineWidth(4f);
            lower_limit.enableDashedLine(10f, 10f, 0f);
            lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            lower_limit.setTextSize(10f);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
            leftAxis.addLimitLine(upper_limit);
            leftAxis.addLimitLine(lower_limit);
            leftAxis.setAxisMaxValue(220f);
            leftAxis.setAxisMinValue(-50f);
            //leftAxis.setYOffset(20f);
            leftAxis.enableGridDashedLine(10f, 10f, 0f);
            leftAxis.setDrawZeroLine(false);

            // limit lines are drawn behind data (and not on top)
            leftAxis.setDrawLimitLinesBehindData(true);

            mChart.getAxisRight().setEnabled(false);

            //mChart.getViewPortHandler().setMaximumScaleY(2f);
            //mChart.getViewPortHandler().setMaximumScaleX(2f);

            mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

            //  dont forget to refresh the drawing
            mChart.invalidate();*/

            setSupportActionBar(toolbar);
            redirigirUsuario();
            pieChartEgresos = (PieChart) findViewById(R.id.pieChartEgresos);
            pieChartIngresos = (PieChart) findViewById(R.id.pieChartIngresos);
            hideCharts();
            txtIngresos = (TextView) findViewById(R.id.txtIngresos);
            txtEgresos = (TextView) findViewById(R.id.txtEgresos);
            txtSaldo = (TextView) findViewById(R.id.txtSaldo);
            try {
                gestionActual = find(ModelGestion.class, "usuario = ? ", String.valueOf(usuarioActual.getId())).get(0);
            } catch (Exception ex) {

            }

            spinnerGestion = (Spinner) findViewById(R.id.spinnerGestion);
            spinnerGraficos = (Spinner) findViewById(R.id.spinnerGraficos);
            spinnerGraficos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tiposGraficos));
            obtenerGestiones();
            estiloPieCHart();
            cargarPieChart();
            //accion para FloatAction Button
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accionSalir();
                }
            });

            //evento para controlar el cambio de gestiones
            spinnerGestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    int index = arg0.getSelectedItemPosition();
                    cargargestion();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinnerGraficos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    int index = arg0.getSelectedItemPosition();
                    cargarGraficos();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch(Exception ex)
        {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }


    }



    private void setDataChart() {
        ArrayList<String> xVals =  chartXvalores;

        ArrayList<Entry> yVals = chartYvalores;

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        mChart.setData(data);
    }

    public void redirigirUsuario() {
        if (find(ModelUsuario.class, "activo = ? ", "1").isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
    }

    public void estiloLineChart() {

    }

    private void cargarGraficos() {
        int selectedValue = spinnerGraficos.getSelectedItemPosition();
        switch (selectedValue) {
            case 0:
                hideCharts();
                pieChartIngresos.setVisibility(View.VISIBLE);
                break;
            case 1:
                hideCharts();
                pieChartEgresos.setVisibility(View.VISIBLE);
                break;
        }


    }

    public void hideCharts() {
        pieChartIngresos.setVisibility(View.GONE);
        pieChartEgresos.setVisibility(View.GONE);
    }

    public void obtenerGestiones() {
        usuarioActual = find(ModelUsuario.class, "activo = ? ", "1").get(0);
        listaGestiones = find(ModelGestion.class, "usuario = ? ", String.valueOf(usuarioActual.getId()));

        ArrayAdapter<ModelGestion> adapter =
                new ArrayAdapter<ModelGestion>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaGestiones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGestion.setAdapter(adapter);
    }

    private void cargargestion() {
        gestionActual = (ModelGestion) spinnerGestion.getSelectedItem();
        mostrarDetallesGestion();
        // estiloPieCHart();
        cargarPieChart();

    }

    private void mostrarDetallesGestion() {
        cargarDatosGestion();
        String totalIngresos = String.valueOf(gestionActual.getTotalIngresos());
        String totalEgresos = String.valueOf(gestionActual.getTotalEgresos());
        String saldo = String.valueOf(gestionActual.getSaldo());
        txtIngresos.setText("+" + totalIngresos);
        txtEgresos.setText("-" + totalEgresos);
        if (gestionActual.getSaldo() > 0) {
            txtSaldo.setTextColor(Color.GREEN);
            txtSaldo.setText("+" + saldo);
        } else {
            txtSaldo.setTextColor(Color.RED);
            txtSaldo.setText(saldo);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void desactivarUsuarios() {
        List<ModelUsuario> users = ModelUsuario.listAll(ModelUsuario.class);
        for (ModelUsuario user : users) {
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

    public void cargarDatosGestion() {
        float TotalIngresos = 0;
        float TotalEgresos = 0;
        List<ModelMovimientos> movimientos = find(ModelMovimientos.class, "gestion = ? ", String.valueOf(gestionActual.getId()));
        for (ModelMovimientos mov : movimientos) {
            String categoria= mov.getCategoria().getDescripcion();
            if (mov.getTipo() == 0) {
                TotalEgresos += mov.getValor();
            } else {
                TotalIngresos += mov.getValor();
            }
        }

        gestionActual.setTotalEgresos(TotalEgresos);
        gestionActual.setTotalIngresos(TotalIngresos);
        float Saldo = TotalIngresos - TotalEgresos;
        gestionActual.setSaldo(Saldo);
        gestionActual.save();

    }

    /*public void setPieChartIngresos() {
        pieChartIngresos.setUsePercentValues(false);
        pieChartIngresos.getDescription().setEnabled(false);
        pieChartIngresos.setExtraOffsets(5, 10, 5, 5);
        pieChartIngresos.setDragDecelerationFrictionCoef(0.95f);

        pieChartIngresos.setDrawHoleEnabled(true);
        pieChartIngresos.setHoleColor(Color.WHITE);

        pieChartIngresos.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> valuesIngresos = new ArrayList<>();

        valuesIngresos.add(new PieEntry(350, "Sueldo"));
        valuesIngresos.add(new PieEntry(220, "Mesada"));
        valuesIngresos.add(new PieEntry(800, "Apuesta"));
        valuesIngresos.add(new PieEntry(900, "Otros"));

        PieDataSet dataSet = new PieDataSet(valuesIngresos, "Ingresos");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChartIngresos.setData(data);

    }*/

    public void estiloPieCHart() {
        pieChartEgresos.setUsePercentValues(false);
        pieChartEgresos.getDescription().setEnabled(false);
        pieChartEgresos.setExtraOffsets(5, 10, 5, 5);
        pieChartEgresos.setDragDecelerationFrictionCoef(0.95f);

        pieChartEgresos.setDrawHoleEnabled(true);
        pieChartEgresos.setHoleColor(Color.WHITE);

        pieChartEgresos.setTransparentCircleRadius(61f);

        /*---*/

        pieChartIngresos.setUsePercentValues(false);
        pieChartIngresos.getDescription().setEnabled(false);
        pieChartIngresos.setExtraOffsets(5, 10, 5, 5);
        pieChartIngresos.setDragDecelerationFrictionCoef(0.95f);

        pieChartIngresos.setDrawHoleEnabled(true);
        pieChartIngresos.setHoleColor(Color.WHITE);

        pieChartIngresos.setTransparentCircleRadius(61f);


        /*----*/


    }

    public void cargarPieChart() {
        ArrayList<PieEntry> valuesEgresos = new ArrayList<>();
        ArrayList<PieEntry> valuesIngresos = new ArrayList<>();


        List<ModelMovimientos> movimientos = find(ModelMovimientos.class, "gestion = ? ", String.valueOf(gestionActual.getId()));
        for (ModelMovimientos mov : movimientos) {
            Float valor = Float.valueOf(0);
            List<ModelMovimientos> grupo = find(ModelMovimientos.class, "categoria = ? ", String.valueOf(mov.getCategoria().getId()));
            for (ModelMovimientos aux : grupo) {
                valor += aux.getValor();
            }
            if (mov.getTipo() == 0) {
                valuesEgresos.add(new PieEntry(valor, mov.getCategoria().getDescripcion()));
            } else {
                valuesIngresos.add(new PieEntry(valor, mov.getCategoria().getDescripcion()));
            }
        }

        PieDataSet dataSet = new PieDataSet(valuesEgresos, "Egresos");
        PieDataSet dataSetIngresos = new PieDataSet(valuesIngresos, "Ingresos");


        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);



        /*--*/

        dataSetIngresos.setSliceSpace(3f);
        dataSetIngresos.setSelectionShift(5f);
        dataSetIngresos.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);


        PieData dataIngresos = new PieData(dataSetIngresos);

        dataIngresos.setValueTextSize(10f);
        dataIngresos.setValueTextColor(Color.YELLOW);

        pieChartIngresos.setData(dataIngresos);
        pieChartEgresos.setData(data);


    }

    public void accionSalir() {
        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        startActivity(intent);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}

