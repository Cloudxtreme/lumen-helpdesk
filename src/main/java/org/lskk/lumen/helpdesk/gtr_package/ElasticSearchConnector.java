package org.lskk.lumen.helpdesk.gtr_package;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by user on 7/31/2016.
 */
@Service
public class ElasticSearchConnector {

    private Client client;
    private int indexNameCounter = 0;
    private String firstIndexName;

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

    public boolean createIndex(String indexName){
        CreateIndexRequestBuilder createIndexRequestBuilder = this.client.admin().indices().prepareCreate(indexName);
        CreateIndexResponse response = createIndexRequestBuilder.execute().actionGet();

        return response.isAcknowledged();
    }

    public void updateIndex(String[] columnName, LinkedList masterData, String aliasName){
        this.createIndex("index-"+this.indexNameCounter);

        for(int i=0; i<masterData.size(); i++){
            Object[] arr = (Object[])masterData.get(i);
            for(int j=0; j<arr.length; j++){
                this.addDataToIndex("index-"+this.indexNameCounter, "core2", columnName[j], arr[j]);
            }
        }

        if(this.indexNameCounter == 0){
            this.changeAlias(this.firstIndexName, "index-"+this.indexNameCounter, aliasName);
            this.deleteIndex(this.firstIndexName);
        }else {
            int oldIndexCounter = this.indexNameCounter - 1;
            this.changeAlias("index-" + oldIndexCounter, "index-" + this.indexNameCounter, aliasName);
            this.deleteIndex("index-" + oldIndexCounter);

            if (this.indexNameCounter == 1000) {
                this.firstIndexName = "index-"+this.indexNameCounter;
                this.indexNameCounter = 0;
            } else {
                this.indexNameCounter++;
            }
        }
    }

    public boolean addDataToIndex(String indexName, String typeName, String field, Object data){
        BulkRequestBuilder bulkRequest = this.client.prepareBulk();

        try {
            bulkRequest.add(this.client.prepareIndex(indexName, typeName)
                .setSource(jsonBuilder()
                        .startObject()
                        .field(field, data)
                        .endObject()
                )
            );
        }catch(Exception e){
            e.printStackTrace();
        }

        BulkResponse bulkResponse = bulkRequest.get();

        return bulkResponse.hasFailures();
    }

    private void changeAlias(String oldIndex, String newIndex, String aliasName){
        this.client.admin().indices().prepareAliases().addAlias(newIndex, aliasName).removeAlias(oldIndex, aliasName).execute().actionGet();
    }

    private boolean deleteIndex(String indexName){
        DeleteIndexRequestBuilder deleteIndexRequestBuilder = this.client.admin().indices().prepareDelete(indexName);
        DeleteIndexResponse response = deleteIndexRequestBuilder.execute().actionGet();

        return response.isAcknowledged();
    }

    //reference = http://stackoverflow.com/questions/25349947/how-to-find-index-by-alias-in-elasticsearch-java-api
//    private Set<String> getIndexBasedOnAlias(String aliasName){
//        IndicesAdminClient iac = this.client.admin().indices();
//        ImmutableOpenMap<String, List<AliasMetaData>> map = iac.getAliases(new GetAliasesRequest(aliasName))
//                .actionGet().getAliases();
//
//        final Set<String> allIndices = new HashSet<>();
//        map.keysIt().forEachRemaining(allIndices::add);
//        return allIndices;
//    }

    public void setFirstIndexName(String firstIndexName){
        this.firstIndexName = firstIndexName;
    }

    public void setIndexNameCounter(int indexNameCounter){
        this.indexNameCounter = indexNameCounter;
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



//    references => http://stackoverflow.com/questions/31345586/how-to-reindex-in-elasticsearch-via-java-api
//    reindex (copy document data) from old index to new index
    private void reindex(String oldIndex, String newIndex){
        // Set up scroll search that would "load" data from old index
        SearchResponse scrollResp = client.prepareSearch(oldIndex) // Specify index
                .setSearchType(SearchType.SCAN)
                .setScroll(new TimeValue(60000))
                .setQuery(QueryBuilders.matchAllQuery()) // Match all query
                .setSize(100).execute().actionGet(); //100 hits per shard will be returned for each scroll

        // Set up bulk processor.
        int BULK_ACTIONS_THRESHOLD = 1000;
        int BULK_CONCURRENT_REQUESTS = 1;
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
//                loggger.info("Bulk Going to execute new bulk composed of {} actions", request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
//                logger.info("Executed bulk composed of {} actions", request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
//                logger.warn("Error executing bulk", failure);
            }
        }).setBulkActions(BULK_ACTIONS_THRESHOLD).setConcurrentRequests(BULK_CONCURRENT_REQUESTS).setFlushInterval(TimeValue.timeValueMillis(5)).build();

        // Read from old index via created scroll searcher in Step 1 until there are mo records left and insert into new index
        // Scroll until no hits are returned
        while (true) {
            scrollResp = this.client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {
//                logger.info("Closing the bulk processor");
                bulkProcessor.close();
                break;
            }
            // Get results from a scan search and add it to bulk ingest
            for (SearchHit hit: scrollResp.getHits()) {
                IndexRequest request = new IndexRequest(newIndex, hit.type(), hit.id());
                Map source = ((Map) ((Map) hit.getSource()));
                request.source(source);
                bulkProcessor.add(request);
            }
        }

        // To assign alias to new index
        this.client.admin().indices().prepareAliases().addAlias(newIndex, "alias_name").get();

        // Remove alias from old index and then delete old index
        this.client.admin().indices().prepareAliases().removeAlias(oldIndex, "alias_name").execute().actionGet();
        this.client.admin().indices().prepareDelete(oldIndex).execute().actionGet();
    }

    public void endConnection(){
        this.client.close();
    }

}
