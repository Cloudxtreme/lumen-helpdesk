package org.lskk.lumen.helpdesk.gtr_package.WebAnalytic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Created by user on 9/21/2016.
 */
@SpringBootApplication(exclude = {GroovyTemplateAutoConfiguration.class, MongoAutoConfiguration.class,
        LiquibaseAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
