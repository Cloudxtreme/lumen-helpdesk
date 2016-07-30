package org.lskk.lumen.helpdesk;

//import org.lskk.lumen.core.LumenCoreConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Aplikasi utama untuk menjalankan helpdesk.
 */
@SpringBootApplication(exclude = {GroovyTemplateAutoConfiguration.class, LiquibaseAutoConfiguration.class})
@Profile({"helpdeskApp"})
//@Import(LumenCoreConfig.class)
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
