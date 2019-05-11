package com.example.franz.wallet.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.franz.wallet.Adapters.CategoriasListAdapter;
import com.example.franz.wallet.Model.ModelCategoria;
import com.example.franz.wallet.Model.ModelUsuario;
import com.example.franz.wallet.R;

import java.util.List;

public class abm_categoria_activity extends AppCompatActivity {

    private static final int DATA_ENVIO_CODE = 1;
    private ListView CategoriasList;
    ModelUsuario usuarioActual = ModelUsuario.find(ModelUsuario.class, "activo = ? ", "1").get(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abm_categoria_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CategoriasList = findViewById(R.id.categoriasList);

        CategoriasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                ModelCategoria ccateg = (ModelCategoria) adapterView.getAdapter().getItem(index);
                // Intent intent = new Intent(Lista.this, ContactActivity.class);
                // intent.putExtra(ContactActivity.PARAM_ID, obj.getContactoId());
                //   startActivity(intent);

            }
        });
        registerForContextMenu(CategoriasList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoriaActivity.class);
                startActivityForResult(intent, DATA_ENVIO_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarCategorias();
    }

    private void cargarCategorias() {
        List<ModelCategoria> categs = ModelCategoria.find(ModelCategoria.class, "usuario = ? ", String.valueOf(usuarioActual.getId()));
        CategoriasListAdapter adapter = new CategoriasListAdapter(this, categs);
        CategoriasList.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == CategoriasList.getId())
            getMenuInflater().inflate(R.menu.abm_categoria, menu);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        cargarCategorias();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_editar_categoria) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int categId = (int) info.id+1;
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra(CategoriaActivity.PARAM_ID, String.valueOf(categId));
            startActivityForResult(intent, DATA_ENVIO_CODE);

        }
        if (id == R.id.action_delete_categoria) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final long categId = (long) info.id+1;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Eliminar Categoria")
                    .setMessage("¿Esta seguro(a) que desea eliminar la categoria seleccionada?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                ModelCategoria ctg = ModelCategoria.findById(ModelCategoria.class, (long) categId);
                                ctg.delete();
                                cargarCategorias();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "No se pudo eliminar la categoria"+e, Toast.LENGTH_SHORT).show();


                            }


                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();


        }

        return super.onContextItemSelected(item);
    }

}
