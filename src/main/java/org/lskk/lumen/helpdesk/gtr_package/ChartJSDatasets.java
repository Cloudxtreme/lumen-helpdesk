package org.lskk.lumen.helpdesk.gtr_package;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 9/11/2016.
 */
public class ChartJSDatasets {

    private String label;
    private Object[] data;
    private boolean fill = false;
    private String borderColor;

    public ChartJSDatasets(){
        Random r = new Random();
        borderColor = "#"+(r.nextInt(9)+1)+""+(r.nextInt(9)+1)+""+(r.nextInt(9)+1)+""+(r.nextInt(9)+1)+""+(r.nextInt(9)+1)+""+(r.nextInt(9)+1)+"";
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }
}
