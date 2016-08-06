package org.lskk.lumen.helpdesk.gtr_package;

/**
 * Created by user on 8/1/2016.
 */
public class DataNode {

    private String columnName;
    private String value;

    public DataNode(String columnName, String value){
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName(){
        return this.columnName;
    }

    public String getValue(){
        return this.value;
    }

}
