package org.lskk.lumen.helpdesk.gtr_package;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by user on 7/31/2016.
 */
@Service
public class ElasticSearchConnector {

    private Client client;

    public void startConnection(){
        try {
            //create setting for cluster name
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "cluster-tester")
                    .put("client.transport.sniff", true).build();

            this.client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public LinkedList searchTwitterStatus(String index, String field, LinkedList data){
        LinkedList finalDataToCSV = new LinkedList();
        String[]dataPerRow = new String[8];
        SearchResponse response = null;

        dataPerRow[0] = "twitterstatusid";                          // twitter status id
        dataPerRow[1] = "userscreenname";                           // user screen name
        dataPerRow[2] = "text";                                     // text
        dataPerRow[3] = "creationtimetwitter";                      // creation time twitter
        dataPerRow[4] = "trackingid";                               // tracking ID lapor
        dataPerRow[5] = "title";                                    // title lapor
        dataPerRow[6] = "creationtimelapor";                        // creation time lapor
        dataPerRow[7] = "score";                                    // score query

        finalDataToCSV.add(dataPerRow);

        for(int i=0; i<data.size(); i++) {
            Object[]arr = (Object[])data.get(i);
            response = client.prepareSearch(index)
                    .setQuery(QueryBuilders.matchQuery(field, (String)arr[2]))
                    .execute()
                    .actionGet();
            if(response.getHits().getHits().length != 0) {
                Map<String, Object> firstMatch = response.getHits().getHits()[0].getSource();
                dataPerRow = new String[8];
                dataPerRow[0] = arr[0].toString();                                      // twitter status id
                dataPerRow[1] = arr[1].toString();                                      // user screen name
                dataPerRow[2] = arr[2].toString();                                      // text
                dataPerRow[3] = arr[3].toString();                                      // creation time twitter
                dataPerRow[4] = (String) firstMatch.get("TrackingID");                  // tracking ID lapor
                dataPerRow[5] = (String) firstMatch.get("JudulLaporan");                // title lapor
                dataPerRow[6] = (String) firstMatch.get("created_at");                  // creation time lapor
                dataPerRow[7] = (response.getHits().getHits()[0].getScore()+"");        // score query

                finalDataToCSV.add(dataPerRow);
            }
        }

        return finalDataToCSV;

//        for(SearchHit hit : response.getHits()){
//            found = hit.getSource();
//            System.out.println(found.get("JudulLaporan"));
//        }

    }

    public void endConnection(){
        this.client.close();
    }

}
