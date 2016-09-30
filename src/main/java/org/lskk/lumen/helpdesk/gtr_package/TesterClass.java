package org.lskk.lumen.helpdesk.gtr_package;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.YearMonth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Profile;

import javax.inject.Inject;

/**
 * Created by user on 8/3/2016.
 */
@SpringBootApplication(exclude = {GroovyTemplateAutoConfiguration.class, MongoAutoConfiguration.class,
        LiquibaseAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Profile("TesterClass")
public class TesterClass implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(TesterClass.class);

    @Inject
    private PostgreConnector pc;

    @Inject
    private CSVParser csvParser;

    @Inject
    private ElasticSearchConnector esConnector;

    @Inject
    private ObjectMapper objMapper;

    public static void main(String[] args) {
//        new SpringApplicationBuilder(TesterClass.class)
//                .profiles("TesterClass"/*, "rabbitmq", "drools"*/)
//                .web(false)
//                .run(args);

        try {
            temp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void temp() throws Exception{
        YearMonth ym = new YearMonth(2012, 5);

//        C3Chart topics = new C3Chart();
////        topics.mostMentionedTopicsOrAreas("logstash-2016.07.27","2012-05", new String[]{"menteri"});
//        topics.mostMentionedTopicsOrAreas("logstash-2016.07.27", ym, new String[]{"menteri"});
//        String resultJSON = objMappers.writeValueAsString(topics);
//        log.info("JSON = {}", resultJSON);
//        //System.out.print("JSON = "+resultJSON);

//        ChartJSMain cjs = new ChartJSMain();
//        cjs.mostMentionedTopicsOrAreas("logstash-2016.07.27", ym, new String[]{"menteri"});
//        String cjsResultJSON = new ObjectMapper().writeValueAsString(cjs);
//        log.info("JSON CJS = {}", cjsResultJSON);

//        ChartJSMain totalCasesPerMonth = new ChartJSMain();
//        totalCasesPerMonth.totalCasesPerMonth("logstash-2016.07.27", 2015);
//        String resultJSON3 = new ObjectMapper().writeValueAsString(totalCasesPerMonth);
//        log.info("JSON = {}", resultJSON3);

//        ChartJSMain totalCasesPerDay = new ChartJSMain();
//        totalCasesPerDay.totalCasesPerDay("logstash-2016.07.27", ym);
//        String resultJSON4 = new ObjectMapper().writeValueAsString(totalCasesPerDay);
//        log.info("JSON = {}", resultJSON4);
    }

    @Override
    public void run(String... args) throws Exception {
//        //Correlate Twitter statuses with LAPOR! in ElasticSearch
//        pc.startConnection();
//        LinkedList dataTwitter = pc.selectTwitterStatusOperation("lumen.twitterstatus");
//        pc.endConnection();
//
//        esConnector.startConnection();
//        LinkedList finalDataToCSV = esConnector.searchTwitterStatus("logstash-2016.07.27", "_all", dataTwitter);
//        esConnector.endConnection();
//
//        csvParser.convertToCSV("data/twitter-to-lapor.csv", finalDataToCSV);

//        // Save reply from escalation staff to database (Not Used)
//        pc.startConnection();
//        pc.insertMasterData("lumen.mastertable", new String[]{"id", "nomor"}, new Object[]{4, 444}); // sesuaikan parameter (array nama kolom, array object data)
//        String[] columnName = pc.getMasterColumnName("lumen.mastertable"); //sesuaikan nama tabel
//        LinkedList masterData = pc.selectMaterData("lumen.mastertable"); // sesuaikan nama tabel
//        pc.endConnection();
//
//        //update elasticsearch based on updated database (Not Used)
//        esConnector.startConnection();
//        esConnector.setFirstIndexName("index-2"); // sesuaikan index yang paling pertama dibuat
//        esConnector.setIndexNameCounter(3); // sesuaikan counter agar terus bertambah
//        esConnector.updateIndex(columnName, masterData, "MyAlias"); // sesuaikan nama alias
//        esConnector.endConnection();

        //C3 chart example
//        C3Chart chart = new C3Chart();
//        chart.setData(new C3Data());
//        chart.getData().getColumns().add(new Object[]{"sampah", 1, 2, 3}); // 123 sampai 31
//
//        String result = objMapper.writeValueAsString(chart);
//        log.info("JSON = {}", result);

        //c3 chart ready
//        C3Chart topics = new C3Chart();
////        topics.mostMentionedTopicsOrAreas("logstash-2016.07.27","2012-05", new String[]{"menteri"});
//        topics.mostMentionedTopicsOrAreas("logstash-2016.07.27", YearMonth.now(), new String[]{"menteri"});
//        String resultJSON = objMapper.writeValueAsString(topics);
//        log.info("JSON = {}", resultJSON);
//
//        C3Chart areas = new C3Chart();
//        areas.mostMentionedTopicsOrAreas("logstash-2016.07.27","2015-09", new String[]{"jelambar","kemayoran"});
//        String resultJSON2 = objMapper.writeValueAsString(areas);
//        log.info("JSON = {}", resultJSON2);
//
//        C3Chart totalCasesPerMonth = new C3Chart();
//        totalCasesPerMonth.totalCasesPerMonth("logstash-2016.07.27", 2015);
//        String resultJSON3 = objMapper.writeValueAsString(totalCasesPerMonth);
//        log.info("JSON = {}", resultJSON3);
//
//        C3Chart totalCasesPerDay = new C3Chart();
//        totalCasesPerDay.totalCasesPerDay("logstash-2016.07.27", "2015-02");
//        String resultJSON4 = objMapper.writeValueAsString(totalCasesPerDay);
//        log.info("JSON = {}", resultJSON4);

        //response check and save
//        pc.startConnection();
//        esConnector.startConnection();
//
//        HelpdeskMessage newMessage = new HelpdeskMessage();
//        newMessage.setId(Long.valueOf(1));
//        newMessage.setChannelSenderId(Long.valueOf(1));
//        newMessage.setChannelSenderScreenName("tester");
//        newMessage.setSenderName("testerName");
//        newMessage.setInputText("ini adalah uji coba update response di server");
//        //input kind bingung
//        newMessage.setDistrictUpName("district up name");
//        newMessage.setDistrictName("district name");
//        newMessage.setDistrictId("district id");
//        newMessage.setHospitalId(1);
//        newMessage.setHospitalName("hospital name");
//        newMessage.setHospitalAddress("hospital address");
//        newMessage.setHospitalPhone("hospital phone");
//        newMessage.setHospitalLat((float) 0.01);
//        newMessage.setHospitalLon((float) 0.02);
//        newMessage.setResponseText("ini adalah response text dari server");
//        newMessage.setGmapsUri("gmaps uri");
//
//        HelpdeskMessage exist = pc.checkResponse(newMessage, "lumen.mastertable");
//        if(exist == null){
//            pc.insertResponse(newMessage, "lumen.mastertable");
//            //System.out.println("insert successful");
//        }
//
//        String indexId = esConnector.checkDataExistInIndex("db-master-response", newMessage);
//        if(indexId.equalsIgnoreCase("")) {
//            esConnector.addResponseDataToIndex("db-master-response", "core2", newMessage);
//        }else{
//            esConnector.updateResponseDataInIndex("db-master-response", "core2", indexId, exist.getAskedCount());
//        }
//
//        esConnector.endConnection();
//        pc.endConnection();
    }
}
