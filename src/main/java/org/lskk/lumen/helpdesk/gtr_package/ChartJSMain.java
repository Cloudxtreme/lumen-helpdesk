package org.lskk.lumen.helpdesk.gtr_package;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/11/2016.
 */
@Component @Scope("prototype")
public class ChartJSMain {

    private String[]labels;
    private List<ChartJSDatasets> datasets;

    private Client esClient;

    private void startElasticSearchConnection(){
        try {
            //create setting for cluster name
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "cluster-tester")
                    .put("client.transport.sniff", true).build();

            this.esClient = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void endElasticSearchConnection(){
        this.esClient.close();
    }

    private String parseDate(String date){
        String[] splitDate = date.split("-");
        String parsedDate = null;

        int day = Integer.parseInt(splitDate[2]);
        int month = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[0]);

        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
            parsedDate = dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        }else if(month == 4 || month == 6 || month == 9 || month == 11){
            if(day < 31){
                LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
                parsedDate = dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
            }
        }else{
            //http://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java/18252071#18252071
            int modulo100 = year % 100;
            //http://science.howstuffworks.com/science-vs-myth/everyday-myths/question50.htm
            if ((modulo100 == 0 && year % 400 == 0) || (modulo100 != 0 && year % 4 == 0)) {
                if(day < 30){
                    LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
                    parsedDate = dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
                }
            } else {
                if(day < 29){
                    LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
                    parsedDate = dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
                }
            }
        }

        return parsedDate;
    }

    public void mostMentionedTopicsOrAreas(String indexName, YearMonth monthYear, String[] topicsOrAreas){
        this.startElasticSearchConnection();
        this.labels = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        this.datasets = new ArrayList<>();
        Object[] c3dataObject = null;
        SearchResponse response = null;
        ChartJSDatasets tempData = null;
        for(int i=0; i<topicsOrAreas.length; i++){
            tempData = new ChartJSDatasets();
            tempData.setLabel(topicsOrAreas[i]);
            c3dataObject = new Object[31];
            for(int j=0; j<31; j++){
                try {
                    final LocalDate parsedDate = new LocalDate(monthYear.getYear(), monthYear.getMonthOfYear(), j+1);
                    //                String parsedDate = this.parseDate(monthYear+"-"+j);
                    QueryBuilder qb = null;
                    //                if(parsedDate != null) {
//                    qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("IsiLaporan", topicsOrAreas[i]))
//                            .must(QueryBuilders.matchPhraseQuery("TanggalLaporanMasuk", parsedDate.toString()));
                    qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("IsiLaporan", topicsOrAreas[i]))
                            .must(QueryBuilders.matchPhraseQuery("TanggalLaporanMasuk", parsedDate.toString("dd MMMM yyyy")));

                    response = this.esClient.prepareSearch(indexName)
                            .setQuery(qb)
                            .execute()
                            .actionGet();

                    c3dataObject[j] = response.getHits().getTotalHits();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    c3dataObject[j] = 0;
                }
            }

            tempData.setData(c3dataObject);

            datasets.add(tempData);
        }

        this.endElasticSearchConnection();
    }

    public void totalCasesPerMonth(String indexName, int year) {
        this.startElasticSearchConnection();

        this.labels = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        this.datasets = new ArrayList<>();
        Object[] c3dataObject = new Object[12];
        SearchResponse response = null;
        ChartJSDatasets tempData = new ChartJSDatasets();
        tempData.setLabel(year+"");

        for(int i=0; i<12; i++){
            String parsedDate = this.parseDate(year+"-"+(i+1)+"-1");
            QueryBuilder qb = null;
            if(parsedDate != null) {
                String[] splitParsedDate = parsedDate.split(" ");
                qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("TanggalLaporanMasuk", splitParsedDate[1]+" "+year));

                response = this.esClient.prepareSearch(indexName)
                        .setQuery(qb)
                        .execute()
                        .actionGet();

                c3dataObject[i] = response.getHits().getTotalHits();
            }else{
                c3dataObject[i] = 0;
            }
        }

        tempData.setData(c3dataObject);
        datasets.add(tempData);

        this.endElasticSearchConnection();
    }

    public void totalCasesPerDay(String indexName, YearMonth monthYear){
        this.startElasticSearchConnection();

        this.labels = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        this.datasets = new ArrayList<>();
        Object[] c3dataObject = new Object[31];
        ChartJSDatasets tempData = new ChartJSDatasets();
        tempData.setLabel(monthYear+"");
        SearchResponse response = null;
        String parsedDate = null;

//        String[] splitParsedDate = this.parseDate(monthYear+"-1").split(" ");

        for(int i=0; i<31; i++){
            parsedDate = this.parseDate(monthYear+"-"+(i+1));
            System.out.println(parsedDate);

            QueryBuilder qb = null;
            if(parsedDate != null) {
                qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("TanggalLaporanMasuk", parsedDate));

                response = this.esClient.prepareSearch(indexName)
                        .setQuery(qb)
                        .execute()
                        .actionGet();

                c3dataObject[i] = response.getHits().getTotalHits();
            }else{
                c3dataObject[i] = 0;
            }
        }

        tempData.setData(c3dataObject);
        datasets.add(tempData);

        this.endElasticSearchConnection();
    }

    public String[] getLabels() {
        return labels;
    }

    public List<ChartJSDatasets> getDatasets() {
        return datasets;
    }
}
