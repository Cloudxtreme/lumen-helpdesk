package org.lskk.lumen.helpdesk.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.internal.config.ConfigEntry;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by ceefour on 01/08/2016.
 */
//@Configuration
public class SparkConfig {

    @Bean(destroyMethod = "stop")
    public SparkContext sparkContext() {
        final String master = "local[*]";
        final SparkConf conf = new SparkConf().setAppName("lumen-helpdesk").setMaster(master)
                .set("spark.sql.warehouse.dir", new File("spark-warehouse").getAbsoluteFile().toURI().toString());
        return new SparkContext(conf);
    }

    @Bean(destroyMethod = "close")
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkContext());
    }

    @Bean(destroyMethod = "stop")
    public SparkSession sparkSession() {
        final SparkSession sparkSession = SparkSession.builder()
                .sparkContext(sparkContext())
//                .appName("lumen-helpdesk")
//                .config("spark.some.config.option", "value")
                .getOrCreate();
        return sparkSession;
    }
}
