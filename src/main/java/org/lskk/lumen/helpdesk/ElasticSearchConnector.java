package org.lskk.lumen.helpdesk;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.net.InetAddress;
import java.util.Map;

/**
 * Created by user on 7/31/2016.
 */
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

    public double search(String index, String field, String text){
        SearchResponse response = client.prepareSearch(index)
                .setQuery(QueryBuilders.matchQuery(field, text))
                .execute()
                .actionGet();

        Map<String, Object> found;

        return response.getHits().getMaxScore();

//        for(SearchHit hit : response.getHits()){
//            found = hit.getSource();
//            System.out.println(found.get("JudulLaporan"));
//        }

    }

    public void endConnection(){
        this.client.close();
    }

}
