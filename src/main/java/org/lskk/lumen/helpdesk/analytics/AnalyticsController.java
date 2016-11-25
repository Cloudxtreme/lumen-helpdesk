package org.lskk.lumen.helpdesk.analytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.MonthDay;
import org.joda.time.YearMonth;
import org.lskk.lumen.helpdesk.gtr_package.C3Chart;
import org.lskk.lumen.helpdesk.gtr_package.ChartJSMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by ceefour on 18/08/2016.
 */
@RestController
@RequestMapping("analytics")
public class AnalyticsController {
    private static Logger log = LoggerFactory.getLogger(AnalyticsController.class);

    @Inject
    private ObjectMapper objMapper;

//    @Inject
//    private Provider<C3Chart> c3ChartProvider;

    @Inject
    private Provider<ChartJSMain> chartJSMainProvider;

    @RequestMapping(method = RequestMethod.GET, path = "mostMentionedTopics", produces = "application/json")
    public ChartJSMain mostMentionedTopics(@RequestParam("topic") String[] upTopics,
                                       @RequestParam("yearMonth") YearMonth yearMonth) throws JsonProcessingException {
        //c3 chart ready
//        C3Chart topics = new C3Chart();
//        C3Chart topics = c3ChartProvider.get();
        ChartJSMain topics = chartJSMainProvider.get();
//        topics.mostMentionedTopicsOrAreas("logstash-2016.07.27","2015-02", new String[]{"sampah","macet"});
        topics.mostMentionedTopicsOrAreas("logstash-2016.07.27", yearMonth, upTopics);
//        String resultJSON = objMapper.writeValueAsString(topics);
//        log.info("JSON = {}", resultJSON);
        return topics;
    }

    @RequestMapping(method = RequestMethod.GET, path = "mostMentionedAreas", produces = "application/json")
    public ChartJSMain mostMentionedAreas(@RequestParam("topic") String[] upTopics,
                                      @RequestParam("yearMonth") YearMonth yearMonth) throws JsonProcessingException {
//        C3Chart areas = new C3Chart();
//        areas.mostMentionedTopicsOrAreas("logstash-2016.07.27","2015-09", new String[]{"jelambar","kemayoran"});
//        C3Chart areas = c3ChartProvider.get();
        ChartJSMain areas = chartJSMainProvider.get();
        areas.mostMentionedTopicsOrAreas("logstash-2016.07.27", yearMonth, upTopics);
        return areas;
//        String resultJSON2 = objMapper.writeValueAsString(areas);
//        log.info("JSON = {}", resultJSON2);

    }

    @RequestMapping(method = RequestMethod.GET, path = "totalCasesPerMonth", produces = "application/json")
    public ChartJSMain totalCasesPerMonth(@RequestParam("year") int year) throws JsonProcessingException {
//        C3Chart totalCasesPerMonth = new C3Chart();
        ChartJSMain totalCasesPerMonth = chartJSMainProvider.get();
        totalCasesPerMonth.totalCasesPerMonth("logstash-2016.07.27", year);
        return totalCasesPerMonth;
//        String resultJSON3 = objMapper.writeValueAsString(totalCasesPerMonth);
//        log.info("JSON = {}", resultJSON3);

    }

    @RequestMapping(method = RequestMethod.GET, path = "totalCasesPerDay", produces = "application/json")
    public ChartJSMain totalCasesPerDay(@RequestParam("yearMonth") YearMonth yearMonth) throws JsonProcessingException {
//        C3Chart totalCasesPerDay = new C3Chart();
//        totalCasesPerDay.totalCasesPerDay("logstash-2016.07.27", "2015-02");
//        C3Chart totalCasesPerDay = c3ChartProvider.get();
        ChartJSMain totalCasesPerDay = chartJSMainProvider.get();
        totalCasesPerDay.totalCasesPerDay("logstash-2016.07.27", yearMonth);
//        String resultJSON4 = objMapper.writeValueAsString(totalCasesPerDay);
//        log.info("JSON = {}", resultJSON4);
        return totalCasesPerDay;
    }
}
