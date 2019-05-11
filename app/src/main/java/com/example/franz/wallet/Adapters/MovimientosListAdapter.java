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

import org.w3c.dom.Text;

import java.util.List;


public class MovimientosListAdapter extends BaseAdapter
{
    private Context context;
    private List<ModelMovimientos> movimientos;

    public MovimientosListAdapter(Context context, List<ModelMovimientos> movs) {
        this.context = context;
        this.movimientos = movs;
    }



    @Override
    public int getCount() {
        return movimientos.size();
    }

    @Override
    public Object getItem(int i) {
        return movimientos.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_movimientos, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else
            holder = (ViewHolder) view.getTag();

        ModelMovimientos mov = movimientos.get(i);
        ModelCategoria categ = mov.getCategoria();

        holder.labelDescripcion.setText(mov.getDescripcion());
        holder.labelCategoria.setText(categ.getDescripcion());
        holder.imgIcono.setText(mov.getFecha().toString());
        if(mov.getTipo()==0)
        {
            holder.labelValor.setTextColor(Color.RED);
            holder.labelValor.setText("-"+String.valueOf(mov.getValor())+" Bs" );
        }else
            {
                holder.labelValor.setTextColor(Color.GREEN);
                holder.labelValor.setText("+"+String.valueOf(mov.getValor())+" Bs" );
            }



        return view;
    }


    class ViewHolder{

        TextView labelDescripcion;
        TextView labelCategoria;
        TextView labelValor;
        TextView imgIcono;

        public ViewHolder(View view){
            labelDescripcion = (TextView) view.findViewById(R.id.descripcionMovimiento);
            labelCategoria = (TextView) view.findViewById(R.id.categoriaMovimiento);
            labelValor = (TextView) view.findViewById(R.id.valorMovimiento);
            imgIcono = (TextView) view.findViewById(R.id.Icon);
        }

    }

}
