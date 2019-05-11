package com.example.franz.wallet.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franz.wallet.Model.ModelCategoria;
import com.example.franz.wallet.Model.ModelMovimientos;
import com.example.franz.wallet.R;

import java.util.List;


public class CategoriasListAdapter extends BaseAdapter
{
    private Context context;
    private List<ModelCategoria> categorias;

    public CategoriasListAdapter(Context context, List<ModelCategoria> categs) {
        this.context = context;
        this.categorias = categs;
    }



    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int i) {
        return categorias.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_categorias, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else
            holder = (ViewHolder) view.getTag();

        ModelCategoria categ = categorias.get(i);


        holder.labelDescripcion.setText(categ.getDescripcion());
        if(categ.getTipo()==0)
        {
            holder.labelTipo.setTextColor(Color.RED);
            holder.labelTipo.setText("Egreso");
        }else
            {
                holder.labelTipo.setTextColor(Color.GREEN);
                holder.labelTipo.setText("Ingreso");
            }




        return view;
    }


    class ViewHolder{

        TextView labelDescripcion;
        TextView labelTipo;


        public ViewHolder(View view){
            labelDescripcion = (TextView) view.findViewById(R.id.lbl_descripcion);
            labelTipo = (TextView) view.findViewById(R.id.lbl_tipo);
        }

    }

}
