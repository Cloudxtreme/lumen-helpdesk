package org.lskk.lumen.helpdesk.submit;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * REST Controller to submit helpdesk job, e.g. incoming message.
 * Created by ceefour on 01/08/2016.
 */
@RestController
public class SubmitController {

    private static Logger log = LoggerFactory.getLogger(SubmitController.class);

    @Inject
    private SparkSession spark;

    @RequestMapping(path = "submit", method = RequestMethod.POST)
    public HelpdeskResult submit(@RequestBody HelpdeskInput input) {
        final long startTime = System.currentTimeMillis();
        final Dataset<Row> inputMessageDf = spark.createDataFrame(input.getMessages(), HelpdeskMessage.class);
        inputMessageDf.printSchema();
        inputMessageDf.show();

        final Dataset<Row> resultDf = inputMessageDf.cache();
        final int elapsedMillis = (int) (System.currentTimeMillis() - startTime);
        final HelpdeskResult helpdeskResult = new HelpdeskResult();
        log.info("resultDf size: {}", resultDf.count());
        resultDf.collectAsList().forEach(row -> {
            final HelpdeskMessage msg = new HelpdeskMessage();
            msg.setId(row.getAs("id"));
            msg.setInputText(row.getAs("inputText"));
            helpdeskResult.getMessages().add(msg);
        });
        helpdeskResult.setElapsedMillis(elapsedMillis);
        return helpdeskResult;
    }

}
