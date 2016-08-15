package org.lskk.lumen.helpdesk.gtr_package;

import org.lskk.lumen.helpdesk.submit.HelpdeskMessage;
import org.lskk.lumen.helpdesk.submit.InputKind;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
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

    //insert data for dynamic table
//    public void insertMasterData(String tableName, String[] columnName, Object[] data){
//        try(Connection c = ds.getConnection()){
//            try(Statement stmt = this.c.createStatement()){
//                String sql = "INSERT INTO "+tableName+" (";
//                for(int i=0; i<columnName.length-1; i++){
//                    sql += columnName[i]+",";
//                }
//                sql += columnName[columnName.length-1]+") VALUES (";
//                for(int i=0; i<data.length-1; i++){
//                    if(data[i] instanceof Integer) {
//                        sql += (int)data[i] + ",";
//                    }else if(data[i] instanceof String){
//                        sql += (String)data[i] + ",";
//                    }else if(data[i] instanceof Timestamp){
//                        sql += (Timestamp)data[i] + ",";
//                    }
//                }
//                sql += data[data.length-1]+");";
////                System.out.println(sql);
//                stmt.executeUpdate(sql);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    public HelpdeskMessage checkResponse(HelpdeskMessage message, String tableName){
        HelpdeskMessage resultMessage = null;
        int id = 0;
        int askedCount = 0;

        try (Connection c = ds.getConnection()) {
            try (Statement stmt = this.c.createStatement()) {
                String tempText = this.preprocessingInput(message.getInputText());
                try (ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE inputtext = '"+tempText+"';")) {
                    while(result.next()){
                        id = result.getInt("id");
                        askedCount = result.getInt("askedcount");
                        this.updateResponse(tableName, askedCount+1, id);
                    }
                }

                if(id != 0) {
                    resultMessage = new HelpdeskMessage();

                    try (ResultSet resultAfterUpdate = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE id = " + id + ";")) {
                        while(resultAfterUpdate.next()) {
                            resultMessage.setId(resultAfterUpdate.getLong("id"));
                            resultMessage.setChannelSenderId(resultAfterUpdate.getLong("channelsenderid"));
                            resultMessage.setChannelSenderScreenName(resultAfterUpdate.getString("channelsenderscreenname"));
                            resultMessage.setSenderName(resultAfterUpdate.getString("sendername"));
                            resultMessage.setInputText(resultAfterUpdate.getString("inputtext"));

                            String inputKindString = resultAfterUpdate.getString("inputkind");
                            resultMessage.setInputKind(InputKind.valueOf(inputKindString));

                            resultMessage.setDistrictUpName(resultAfterUpdate.getString("districtupname"));
                            resultMessage.setDistrictName(resultAfterUpdate.getString("districtname"));
                            resultMessage.setDistrictId(resultAfterUpdate.getString("districtid"));
                            resultMessage.setHospitalId(resultAfterUpdate.getInt("hospitalid"));
                            resultMessage.setHospitalName(resultAfterUpdate.getString("hospitalname"));
                            resultMessage.setHospitalAddress(resultAfterUpdate.getString("hospitaladdress"));
                            resultMessage.setHospitalPhone(resultAfterUpdate.getString("hospitalphone"));
                            resultMessage.setHospitalLat(resultAfterUpdate.getFloat("hospitallat"));
                            resultMessage.setHospitalLon(resultAfterUpdate.getFloat("hospitallon"));
                            resultMessage.setResponseText(resultAfterUpdate.getString("responsetext"));
                            resultMessage.setGmapsUri(resultAfterUpdate.getString("gmapsuri"));
                            resultMessage.setAskedCount(resultAfterUpdate.getInt("askedcount"));
                        }
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

        return resultMessage;
    }

    public void insertResponse(HelpdeskMessage message, String tableName){
        try(Connection c = ds.getConnection()){
            try(Statement stmt = c.createStatement()){
                String sql = "INSERT INTO "+tableName+" (id, channelsenderid, channelsenderscreenname, sendername, inputtext, inputkind, districtupname, districtname, districtid," +
                        "hospitalid, hospitalname, hospitaladdress, hospitalphone, hospitallat, hospitallon, responsetext, gmapsuri, askedcount) VALUES (";

                sql += message.getId()+",";
                sql += message.getChannelSenderId()+",";
                sql += "'"+message.getChannelSenderScreenName()+"',";
                sql += "'"+message.getSenderName()+"',";
                sql += "'"+this.preprocessingInput(message.getInputText())+"',";
                sql += "'"+InputKind.valueOf("UNKNOWN")+"',";
                sql += "'"+message.getDistrictUpName()+"',";
                sql += "'"+message.getDistrictName()+"',";
                sql += "'"+message.getDistrictId()+"',";
                sql += message.getHospitalId()+",";
                sql += "'"+message.getHospitalName()+"',";
                sql += "'"+message.getHospitalAddress()+"',";
                sql += "'"+message.getHospitalPhone()+"',";
                sql += message.getHospitalLat()+",";
                sql += message.getHospitalLon()+",";
                sql += "'"+message.getResponseText()+"',";
                sql += "'"+message.getGmapsUri()+"',";
                sql += "1);";
                //System.out.println(sql);
                stmt.executeUpdate(sql);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void updateResponse(String tableName, int askedCount, int id){
        try (Connection c = ds.getConnection()) {
            try (Statement stmt = this.c.createStatement()) {
                String sql = "UPDATE " + tableName + " set ASKEDCOUNT = "+askedCount+" WHERE id = "+id+";";
                stmt.executeUpdate(sql);
                c.commit();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }


        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private String preprocessingInput(String text){
        String preporcessing = text.replaceAll("\\P{L}+","");

        return preporcessing.toLowerCase();
    }

//    public String[] getMasterColumnName(String tableName){
//        String[] columnName = null;
//        int columnCount = 0;
//        try (Connection c = ds.getConnection()) {
//            try (Statement stmt = this.c.createStatement()) {
//                try (ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + ";")) {
//                    columnCount = result.getMetaData().getColumnCount();
//                    columnName = new String[columnCount];
//                    for(int i=0; i<columnCount; i++){
//                        columnName[i] = result.getMetaData().getColumnName(i+1);
//                    }
//                }
//            } catch (Exception e) {
//                System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                System.exit(0);
//            }
//
//
//        }catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//
//        return columnName;
//    }
//
//    public LinkedList selectMaterData(String tableName){
//        LinkedList allData = null;
//        Object[] dataPerRow = null;
//        int columnCount = 0;
//        int counter = 1;
//        try (Connection c = ds.getConnection()) {
//            try (Statement stmt = this.c.createStatement()) {
//                allData = new LinkedList();
//                try (ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + ";")) {
//                    columnCount = result.getMetaData().getColumnCount();
//                    while (result.next()) {
//                        dataPerRow = new Object[columnCount];
//                        for(int j=0; j<columnCount; j++){
//                            dataPerRow[j] = result.getObject(j+1);
//                        }
//                        allData.add(dataPerRow);
//                    }
//                }
//            } catch (Exception e) {
//                System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                System.exit(0);
//            }
//
//
//        }catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//
//        return allData;
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
