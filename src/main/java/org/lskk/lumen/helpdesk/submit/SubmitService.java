package org.lskk.lumen.helpdesk.submit;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.MapPartitionsFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.lskk.lumen.helpdesk.jsc.JscService;
import org.lskk.lumen.helpdesk.lapor.LaporService;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Asks both {@link org.lskk.lumen.helpdesk.jsc.JscService} and {@link org.lskk.lumen.helpdesk.lapor.LaporService}.
 * Created by ceefour on 02/08/2016.
 */
@Service
public class SubmitService {

    private static Logger log = LoggerFactory.getLogger(SubmitService.class);

    @Inject
    private JscService jscService;
    @Inject
    private LaporService laporService;

    public HelpdeskResult submit(HelpdeskInput input) {
        final long startTime = System.currentTimeMillis();

        final HelpdeskResult jscResult = jscService.submit(input);
        final HelpdeskResult laporResult = laporService.search(input);

        // Prioritize JSC result if possible
        final HelpdeskResult helpdeskResult = jscResult;
        for (int i = 0; i < helpdeskResult.getMessages().size(); i++) {
            if (null == helpdeskResult.getMessages().get(i).getResponseText()) {
                helpdeskResult.getMessages().set(i, laporResult.getMessages().get(i));
            }
        }

        final int elapsedMillis = (int) (System.currentTimeMillis() - startTime);
        helpdeskResult.setElapsedMillis(elapsedMillis);
        return helpdeskResult;
    }

}
