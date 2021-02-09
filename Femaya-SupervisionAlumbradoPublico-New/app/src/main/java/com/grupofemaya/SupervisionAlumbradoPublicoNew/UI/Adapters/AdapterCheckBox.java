package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CheckBoxItem;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.List;

public class AdapterCheckBox extends RecyclerView.Adapter<AdapterCheckBox.ViewHolder> {
    private final List<CheckBoxItem> mList;
    private OnItemSelectedListener mItemSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(String text, boolean isChecked);
    }

    public AdapterCheckBox(List<CheckBoxItem> list, OnItemSelectedListener mItemSelectedListener) {
        this.mList = list;
        this.mItemSelectedListener = mItemSelectedListener;
    }

    @NonNull
    @Override
    public AdapterCheckBox.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkbox, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCheckBox.ViewHolder holder, int position) {
        holder.chkBox.setText(mList.get(position).getText());

        holder.chkBox.setChecked(mList.get(position).isSaveActive());

        holder.chkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                mList.get(position).setSaveActive(true);
                mItemSelectedListener.onItemSelected(holder.chkBox.getText().toString(), true);
            } else {
                mList.get(position).setSaveActive(false);
                mItemSelectedListener.onItemSelected(holder.chkBox.getText().toString(), false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox chkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
