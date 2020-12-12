package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Helpers;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DeductivasDTO;

public class SelectableItem extends DeductivasDTO {
    private boolean isSelected = false;


    public SelectableItem(DeductivasDTO item,boolean isSelected) {
        super(item.getIdPenalty(),item.getPunto());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}