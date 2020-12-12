package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.List;

/**
 * Created by luiscarlin on 5/24/17.
 */
public class AdapterDirectorDamages extends ArrayAdapter<DamageDTO> {


    OnItemSelectedListener itemSelectedListener;



    public interface OnItemSelectedListener {
        void onItemSelected(DamageDTO item);
    }

    private static class ViewHolder {
        TextView txtButton;
        RelativeLayout btnLayout;
    }

    public AdapterDirectorDamages(Context context, List<DamageDTO> items) {
        super(context, R.layout.item_vialidades, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final DamageDTO item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_vialidades, parent, false);
            viewHolder.txtButton = convertView.findViewById(R.id.txtButton);
            viewHolder.btnLayout = convertView.findViewById(R.id.btnLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            viewHolder.txtButton.setText(item.getDamage());
            viewHolder.btnLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelectedListener.onItemSelected(item);
                }
            });


        }


        return convertView;
    }


    public void setItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

}