package com.example.bvgdeparturewidget;

public class WidgetItem {
    public String text;
    public int icon_id;

    public WidgetItem(String text, int[] ids) {
        this.text = text;
        if (ids.length > 0) {
            icon_id = ids[0];
        } else {
            icon_id = 0;
        }
    }

    public WidgetItem(String text, int id) {
        this.text = text;
        this.icon_id = id;
    }
}
