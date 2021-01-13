package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.List;

/**
 * Created by luiscarlin on 5/24/17.
 */
public class AdapterMaterial extends ArrayAdapter<MaterialDTO> {
    OnItemSelectedListener itemSelectedListener;
    OnItemMaterialSelectedListener itemMaterialSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(VialidadDTO item);
    }

    private static class ViewHolder {
        CheckBox txtCheck;
    }

    public interface OnItemMaterialSelectedListener {
        void onItemMaterialSelected(MaterialDTO item);
    }

    public AdapterMaterial(Context context, List<MaterialDTO> items, OnItemMaterialSelectedListener itemMaterialSelectedListener) {
        super(context, R.layout.item_damges, items);
        this.itemMaterialSelectedListener = itemMaterialSelectedListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MaterialDTO item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_damges, parent, false);
            viewHolder.txtCheck = convertView.findViewById(R.id.txtCheck);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            viewHolder.txtCheck.setText(item.getMaterial());
            viewHolder.txtCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if(isChecked){
                            LiveData.getInstance().getLiveReport().getListaMaterial().add(item);
                            itemMaterialSelectedListener.onItemMaterialSelected(item);
                        }else{
                            LiveData.getInstance().getLiveReport().getListaMaterial().remove(item);
                        }

                    }
                }
            );
        }
        return convertView;
    }
}