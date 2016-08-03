package org.lskk.lumen.helpdesk;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by user on 7/31/2016.
 */
public class CSVParser {

    private int lineCount;
    private String[]columnName;
    private LinkedList data;

    public CSVParser(){
        this.lineCount = 0;
        this.data = new LinkedList();
    }

    public void parseCSV(String path){

        try (CSVReader reader = new CSVReader(new FileReader(path))){
            String[]line;

            line = reader.readNext();
            if(line != null){
                this.columnName = new String[line.length];
                for(int i=0; i<line.length; i++){
                    this.columnName[i] = line[i];
                }

                this.lineCount++;
            }

            DataNode[] dataNode;

            while((line = reader.readNext()) != null){
                dataNode = new DataNode[line.length];
                for(int i=0; i<line.length; i++){
                    dataNode[i] = new DataNode(this.columnName[i], line[i]);
                }
                this.data.add(dataNode);

                this.lineCount++;
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public LinkedList getData(){
        return this.data;
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public String[] getColumnName(){
        return this.columnName;
    }

}
