package org.lskk.lumen.helpdesk.submit;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.MapPartitionsFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mvel2.sh.command.basic.Help;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * REST Controller to submit helpdesk job, e.g. incoming message.
 * Created by ceefour on 01/08/2016.
 */
@RestController
public class SubmitController {

    private static Logger log = LoggerFactory.getLogger(SubmitController.class);

    @Inject
    private SparkSession spark;
    @Inject
    DataSourceProperties dataSourceProps;

    @RequestMapping(path = "submit", method = RequestMethod.POST)
    public HelpdeskResult submit(@RequestBody HelpdeskInput input) {
        final long startTime = System.currentTimeMillis();

        final Dataset<HelpdeskMessage> inputMessageDf = spark.createDataset(input.getMessages(), Encoders.kryo(HelpdeskMessage.class));
        inputMessageDf.printSchema();
        inputMessageDf.show();

        Dataset<HelpdeskMessage> resultDf;
        resultDf = inputMessageDf.map((MapFunction<HelpdeskMessage, HelpdeskMessage>) (row -> {
            final Pattern GENERAL_HOSPITAL_IN_DISTRICT = Pattern.compile("(rumah sakit|RS|rumkit)( di)? (kecamatan|kec|kec.) (?<districtName>.+)", Pattern.CASE_INSENSITIVE);
            final Matcher matcher = GENERAL_HOSPITAL_IN_DISTRICT.matcher(row.getInputText());
            if (matcher.matches()) {
                row.setInputKind(InputKind.GENERAL_HOSPITAL_IN_DISTRICT);
                row.setDistrictUpName(matcher.group("districtName"));
            } else {
                row.setInputKind(InputKind.UNKNOWN);
            }
            return row;
        }), Encoders.kryo(HelpdeskMessage.class));
        final String dsUrl = dataSourceProps.getUrl();
        final String dsUsername = dataSourceProps.getUsername();
        final String dsPassword = dataSourceProps.getPassword();
        resultDf = resultDf.mapPartitions((MapPartitionsFunction<HelpdeskMessage, HelpdeskMessage>)(iter -> {
            final ArrayList<HelpdeskMessage> resultPartition = new ArrayList<>();
            final PoolProperties poolProps = new PoolProperties();
            poolProps.setUrl(dsUrl);
            poolProps.setDriverClassName(Driver.class.getName());
            poolProps.setUsername(dsUsername);
            poolProps.setPassword(dsPassword);
            final DataSource ds = new DataSource(poolProps);
            try {
                try (Connection conn = ds.getConnection()) {
                    final JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
                    iter.forEachRemaining(row -> {
                        if (null != row.getDistrictUpName()) {
                            log.trace("Finding district '{}'...", row.getDistrictUpName());
                            @Nullable
                            final Map<String, Object> district = jdbcTemplate.queryForMap("SELECT id, name FROM lumen.district WHERE LOWER(name) = ?",
                                    row.getDistrictUpName().toLowerCase());
                            if (null != district) {
                                row.setDistrictId((String) district.get("id"));
                                row.setDistrictName((String) district.get("name"));

                                // get the hospital
                                log.trace("Finding hospital in '{}'...", row.getDistrictId());
                                @Nullable
                                final Map<String, Object> hospital = jdbcTemplate.queryForMap("SELECT * FROM lumen.general_hospital WHERE district_id = ? LIMIT 1",
                                        row.getDistrictId());
                                if (null != hospital) {
                                    row.setHospitalId((Integer) hospital.get("id"));
                                    row.setHospitalName((String) hospital.get("name"));
                                    row.setHospitalAddress((String) hospital.get("location_address"));
                                    row.setHospitalPhone((String) hospital.get("phone0"));
                                    row.setHospitalLat((Float) hospital.get("lat"));
                                    row.setHospitalLon((Float) hospital.get("lon"));
                                }
                            }
                        }
                        resultPartition.add(row);
                    });
                }
            } finally {
                ds.close();
            }
            return resultPartition.iterator();
        }), Encoders.kryo(HelpdeskMessage.class));

        resultDf = resultDf.map((MapFunction<HelpdeskMessage, HelpdeskMessage>) (row -> {
            if (null != row.getHospitalId()) {
                row.setResponseText(String.format("Rumah Sakit %s, %s, %s \uD83D\uDCDE %s",
                        row.getHospitalName(), row.getHospitalAddress(), row.getDistrictName(), row.getHospitalPhone()));
                final String gmapsQuery = String.format("Rumah Sakit %s, %s, %s, Jakarta",
                        row.getHospitalName(), row.getHospitalAddress(), row.getDistrictName());
                row.setGmapsUri("http://maps.google.co.id/?q=" + URLEncoder.encode(gmapsQuery, "UTF-8"));
            }
            return row;
        }), Encoders.kryo(HelpdeskMessage.class));

//        Dataset<HelpdeskMessage> resultDf = inputMessageDf.cache();
        final int elapsedMillis = (int) (System.currentTimeMillis() - startTime);
        final HelpdeskResult helpdeskResult = new HelpdeskResult();
//        log.info("resultDf size: {}", resultDf.count());
        resultDf.collectAsList().forEach(row -> {
//            final HelpdeskMessage msg = new HelpdeskMessage();
//            msg.setId(row.getAs("id"));
//            msg.setInputText(row.getAs("inputText"));
            helpdeskResult.getMessages().add(row);
        });
        helpdeskResult.setElapsedMillis(elapsedMillis);
        return helpdeskResult;
    }

}
