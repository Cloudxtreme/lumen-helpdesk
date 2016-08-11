package org.lskk.lumen.helpdesk.gtr_package;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Profile;

import javax.inject.Inject;
import java.util.LinkedList;

/**
 * Created by user on 8/3/2016.
 */
@SpringBootApplication(exclude = {GroovyTemplateAutoConfiguration.class, MongoAutoConfiguration.class,
        LiquibaseAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Profile("TesterClass")
public class TesterClass implements CommandLineRunner {
    @Inject
    private PostgreConnector pc;

    @Inject
    private CSVParser csvParser;

    @Inject
    private ElasticSearchConnector esConnector;

    public static void main(String[] args) {
        new SpringApplicationBuilder(TesterClass.class)
                .profiles("TesterClass"/*, "rabbitmq", "drools"*/)
                .web(false)
                .run(args);

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

//        // Save reply from escalation staff to database
//        pc.startConnection();
//        pc.insertMasterData("lumen.mastertable", new String[]{"id", "nomor"}, new Object[]{4, 444}); // sesuaikan parameter (array nama kolom, array object data)
//        String[] columnName = pc.getMasterColumnName("lumen.mastertable"); //sesuaikan nama tabel
//        LinkedList masterData = pc.selectMaterData("lumen.mastertable"); // sesuaikan nama tabel
//        pc.endConnection();
//
//        //update elasticsearch based on updated database
//        esConnector.startConnection();
//        esConnector.setFirstIndexName("index-2"); // sesuaikan index yang paling pertama dibuat
//        esConnector.setIndexNameCounter(3); // sesuaikan counter agar terus bertambah
//        esConnector.updateIndex(columnName, masterData, "MyAlias"); // sesuaikan nama alias
//        esConnector.endConnection();
    }
}
