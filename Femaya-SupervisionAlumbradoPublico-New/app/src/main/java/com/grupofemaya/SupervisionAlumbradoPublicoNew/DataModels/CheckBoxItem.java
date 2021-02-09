package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;

public class CheckBoxItem {
    private String text;
    private boolean saveActive = false;

    public CheckBoxItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSaveActive() {
        return saveActive;
    }

    public void setSaveActive(boolean saveActive) {
        this.saveActive = saveActive;
    }
}
