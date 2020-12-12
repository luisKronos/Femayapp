package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CarDTO;
import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.ArrayList;

/**
 * Created by luiscarlin on 5/24/17.
 */
public class AdapterVehiculos extends ArrayAdapter<CarDTO> {

    Funcs mFuncs = new Funcs();

    private static class ViewHolder {
        TextView txtPlates;
        TextView txTorretas;
        TextView txtLuces;
        TextView txtLaminas;
        TextView txtLonas;
        ImageView imgView;
    }

    public AdapterVehiculos(Context context, ArrayList<CarDTO> users) {
        super(context, R.layout.item_cars, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CarDTO carItem = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_cars, parent, false);
            viewHolder.txtPlates = convertView.findViewById(R.id.txtPlates);
            viewHolder.txTorretas = convertView.findViewById(R.id.txTorretas);
            viewHolder.txtLuces = convertView.findViewById(R.id.txtLuces);
            viewHolder.txtLaminas = convertView.findViewById(R.id.txtLaminas);
            viewHolder.txtLonas = convertView.findViewById(R.id.txtLonas);
            viewHolder.imgView = convertView.findViewById(R.id.imgView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (carItem != null) {
            viewHolder.txtPlates.setText(carItem.getPlacas());
            viewHolder.txTorretas.setText(convertBoolean(carItem.getTorretas()));
            viewHolder.txtLuces.setText(convertBoolean(carItem.getLuces()));
            viewHolder.txtLaminas.setText(convertBoolean(carItem.getLaminas()));
            viewHolder.txtLonas.setText(convertBoolean(carItem.getLona()));
            mFuncs.setImageOnImageView(carItem.getImagen(),viewHolder.imgView);

        }


        return convertView;
    }


    private String convertBoolean(String data){
        if(data.equals("1")){
            return "CUMPLE";
        }else{
            return "NO CUMPLE";
        }

    }
}