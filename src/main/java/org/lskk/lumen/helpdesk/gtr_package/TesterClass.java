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

    public static void main(String[] args) {
        new SpringApplicationBuilder(TesterClass.class)
                .profiles("TesterClass"/*, "rabbitmq", "drools"*/)
                .web(false)
                .run(args);

    }

    @Override
    public void run(String... args) throws Exception {
//        CSVParser parser = new CSVParser();
//        parser.parseCSV("twitter.csv");
//        LinkedList data = parser.getData();

//        PostgreConnector pc = new PostgreConnector();
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
}
