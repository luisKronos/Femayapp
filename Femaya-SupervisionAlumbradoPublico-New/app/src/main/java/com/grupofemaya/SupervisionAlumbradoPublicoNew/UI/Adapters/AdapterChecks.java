package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSStatusCheck;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.List;

/**
 * Created by luiscarlin on 5/24/17.
 */
public class AdapterChecks extends ArrayAdapter<RSStatusCheck> {


    private static class ViewHolder {
        TextView txtTime;
        TextView txtRoad;
    }

    public AdapterChecks(Context context, List<RSStatusCheck> data) {
        super(context, R.layout.item_check, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RSStatusCheck item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_check, parent, false);
            viewHolder.txtTime = convertView.findViewById(R.id.txtTime);
            viewHolder.txtRoad = convertView.findViewById(R.id.txtRoad);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            viewHolder.txtRoad.setText(item.getIdCheck() + " - " + item.getRoad());
            viewHolder.txtTime.setText(item.getCreatedTime());
        }


        return convertView;
    }

}