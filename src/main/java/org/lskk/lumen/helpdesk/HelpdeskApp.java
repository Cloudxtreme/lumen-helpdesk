package org.lskk.lumen.helpdesk;

//import org.lskk.lumen.core.LumenCoreConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Profile;

import java.util.LinkedList;

/**
 * Aplikasi utama untuk menjalankan helpdesk.
 */
@SpringBootApplication(exclude = {GroovyTemplateAutoConfiguration.class, MongoAutoConfiguration.class})

@Profile({"helpdeskApp"})
//@Import(LumenCoreConfig.class)
@EnableCaching
class HelpdeskApp implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(HelpdeskApp.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(HelpdeskApp.class)
                .profiles("helpdeskApp"/*, "rabbitmq", "drools"*/)
                .run(args);

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Joining thread, you can press Ctrl+C to shutdown application");
        Thread.currentThread().join();
    }
}
