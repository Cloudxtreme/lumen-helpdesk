package org.lskk.lumen.helpdesk.gtr_package.WebAnalytic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.YearMonth;
import org.lskk.lumen.helpdesk.gtr_package.ChartJSMain;
import org.springframework.web.bind.annotation.*;

/**
 * Created by user on 9/27/2016.
 */

@RestController
@CrossOrigin
public class WebAnalyticsController {

//    @RequestMapping("/")
//    public String index() {
//        return "Greetings from Spring Boot!";
//    }

    @RequestMapping(value = "/mostMentionedTopics", method = RequestMethod.GET)
    public String mostMentionedTopicsData(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year, @RequestParam(value = "topics") String topics){
        YearMonth ym = new YearMonth(year, month);

        ChartJSMain cjs = new ChartJSMain();
        cjs.mostMentionedTopicsOrAreas("logstash-2016.07.27", ym, this.splitString(topics));
        String cjsResultJSON = "";
        try {
            cjsResultJSON = new ObjectMapper().writeValueAsString(cjs);
        }catch(Exception e){
            e.printStackTrace();
        }

        return cjsResultJSON;

//        return "Hello World";
    }

    @RequestMapping(value = "/mostMentionedAreas", method = RequestMethod.GET)
    public String mostMentionedAreasData(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year, @RequestParam(value = "areas") String areas){
        YearMonth ym = new YearMonth(year, month);

        ChartJSMain cjs = new ChartJSMain();
        cjs.mostMentionedTopicsOrAreas("logstash-2016.07.27", ym, this.splitString(areas));
        String cjsResultJSON = "";
        try {
            cjsResultJSON = new ObjectMapper().writeValueAsString(cjs);
        }catch(Exception e){
            e.printStackTrace();
        }

        return cjsResultJSON;
    }

    @RequestMapping(value = "/totalCasePerMonth", method = RequestMethod.GET)
    public String totalCasePerMonthData(@RequestParam(value = "year") int year){
        ChartJSMain totalCasesPerMonth = new ChartJSMain();
        totalCasesPerMonth.totalCasesPerMonth("logstash-2016.07.27", year);
        String resultJSON3 = "";
        try {
            resultJSON3 = new ObjectMapper().writeValueAsString(totalCasesPerMonth);
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultJSON3;
    }

    @RequestMapping(value = "/totalCasePerDay", method = RequestMethod.GET)
    public String totalCasePerDayData(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year){
        YearMonth ym = new YearMonth(year, month);

        ChartJSMain totalCasesPerDay = new ChartJSMain();
        totalCasesPerDay.totalCasesPerDay("logstash-2016.07.27", ym);
        String resultJSON4 = "";
        try {
            resultJSON4 = new ObjectMapper().writeValueAsString(totalCasesPerDay);
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultJSON4;
    }

    private String[] splitString(String dataString){
        return dataString.split(",");
    }
}
