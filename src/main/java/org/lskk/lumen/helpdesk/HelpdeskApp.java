package org.lskk.lumen.helpdesk;

//import org.lskk.lumen.core.LumenCoreConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Profile;

import java.util.LinkedList;

/**
 * Aplikasi utama untuk menjalankan helpdesk.
 */
@SpringBootApplication(exclude = {GroovyTemplateAutoConfiguration.class, MongoAutoConfiguration.class})

@Profile({"helpdeskApp"})
//@Import(LumenCoreConfig.class)
class HelpdeskApp implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(HelpdeskApp.class);

    public static void main(String[] args) {
//        new SpringApplicationBuilder(HelpdeskApp.class)
//                .profiles("helpdeskApp"/*, "rabbitmq", "drools"*/)
//                .run(args);

//        CSVParser parser = new CSVParser();
//        parser.parseCSV("twitter.csv");
//        LinkedList data = parser.getData();

        PostgreConnector pc = new PostgreConnector();
        pc.startConnection();
        LinkedList postgreData = pc.selectOperation("lumen.twitterstatus");
        pc.endConnection();

        ElasticSearchConnector esConnector = new ElasticSearchConnector();
        esConnector.startConnection();
        for(int i=0; i<postgreData.size(); i++) {
            String text = (String)postgreData.get(i);
            double maxScore = esConnector.search("logstash-2016.07.27", "_all", text);
            System.out.println(text +" max score = "+ maxScore);
        }
        esConnector.endConnection();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Joining thread, you can press Ctrl+C to shutdown application");
        Thread.currentThread().join();
    }
}
