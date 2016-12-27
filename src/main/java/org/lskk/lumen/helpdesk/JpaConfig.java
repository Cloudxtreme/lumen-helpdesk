package org.lskk.lumen.helpdesk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * Created by ceefour on 25/12/2016.
 */
@Configuration
public class JpaConfig {

    @Inject
    private EntityManagerFactory emf;

    /**
     * Spring Boot insists on creating DataSourceTransactionManager for some reason, which doesn't work well.
     * So we have to create this manually.
     * @return
     */
    @Bean @Primary
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(emf);
    }
}
