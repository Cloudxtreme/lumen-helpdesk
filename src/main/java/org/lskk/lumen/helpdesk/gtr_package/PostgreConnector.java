package org.lskk.lumen.helpdesk.gtr_package;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Created by user on 8/1/2016.
 */
@Service
public class PostgreConnector {

    private Connection c;

    @Inject
    private DataSourceProperties dp;

    @Inject
    private DataSource ds;

    public void startConnection(){
        this.c = null;
        try{
//            Class.forName("org.postgresql.Driver");
//            this.c = DriverManager.getConnection(dp.getUrl(), dp.getUsername(), dp.getPassword());
            this.c = ds.getConnection();
        }catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
//        System.out.println("Database Opened Succesfully");
    }

//    public void createTable(String tableName, String[] columnName){
//        Statement stmt = null;
//        try {
//            stmt = this.c.createStatement();
//
//            String sql = "CREATE TABLE " + tableName +
//                    "(ID INT PRIMARY KEY NOT NULL,";
//
//            for (int i = 1; i < columnName.length-1; i++) {
//                sql += columnName[i] + " TEXT,";
//            }
//
//            sql += columnName[columnName.length-1]+" TEXT)";
//
//            stmt.executeUpdate(sql);
//            stmt.close();
//        }catch(Exception e){
//            System.err.println(e.getClass().getName()+": "+e.getMessage());
//            System.exit(0);
//        }
//
//        System.out.println("Table "+tableName+" Created Successfully");
//    }

//    public void insertOperation(String tableName, String[] columnName, DataNode[] data){
//        Statement stmt = null;
//        try{
//            stmt = this.c.createStatement();
//
//            String sql = "INSERT INTO "+tableName+" (";
//            for(int i=0; i<columnName.length-1; i++){
//                sql += columnName[i]+",";
//            }
//            sql += columnName[columnName.length-1]+") VALUES (";
//            for(int i=0; i<data.length-1; i++){
//                sql += data[i].getValue()+",";
//            }
//            sql += data[data.length-1].getValue()+");";
//
//            System.out.println(sql);
//            stmt.executeUpdate(sql);
//            stmt.close();
//        }catch(Exception e){
//            System.err.println(e.getClass().getName()+": "+e.getMessage());
//            System.exit(0);
//        }
////        System.out.println("Data Inserted Successfully");
//    }

    public LinkedList selectTwitterStatusOperation(String tableName){
        LinkedList allData = null;
        try (Connection c = ds.getConnection()) {
            allData = new LinkedList();
            try (Statement stmt = this.c.createStatement()) {

                try (ResultSet result = stmt.executeQuery("SELECT ts.statusid, ts.userscreenname, ts.text, ts.creationtime FROM " + tableName + " ts;")) {
                    while (result.next()) {
                        Object[]temp = new Object[4];
                        temp[0] = result.getBigDecimal("statusid");
                        temp[1] = result.getString("userscreenname");
                        temp[2] = result.getString("text");
                        temp[3] = result.getTimestamp("creationtime");
                        allData.add(temp);
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }


        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return allData;
    }

    public void endConnection(){
        try{
            if(this.c != null){
                this.c.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
