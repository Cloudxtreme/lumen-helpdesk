package org.lskk.lumen.helpdesk.lapor;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.lskk.lumen.helpdesk.submit.HelpdeskInput;
import org.lskk.lumen.helpdesk.submit.HelpdeskMessage;
import org.lskk.lumen.helpdesk.submit.HelpdeskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Service to search LAPOR.
 * Created by ceefour on 01/08/2016.
 * @see LaporController
 */
@Service
public class LaporService {

    public static final float THRESHOLD = 11f;
    private static Logger log = LoggerFactory.getLogger(LaporService.class);

//    @Inject
//    private LaporCaseRepository laporCaseRepo;
    @Inject
    private ElasticsearchOperations esTemplate;

    public HelpdeskResult search(HelpdeskInput input) {
        final HelpdeskResult helpdeskResult = new HelpdeskResult();
        final long startTime = System.currentTimeMillis();

        input.getMessages().forEach(inputMsg -> {
            final HelpdeskMessage outMsg = search(inputMsg.getInputText());
            outMsg.setId(inputMsg.getId());
            helpdeskResult.getMessages().add(outMsg);
        });
        helpdeskResult.setElapsedMillis((int) (System.currentTimeMillis() - startTime));
        return helpdeskResult;
    }

    public HelpdeskMessage search(String phrase) {
        final long startTime = System.currentTimeMillis();
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("logstash-2016.07.27")
                .withQuery(QueryBuilders.matchQuery("_all", phrase))
                .withMinScore(THRESHOLD)
                .build();
//        laporCaseRepo.search(searchQuery);

//        final HelpdeskResult result = new HelpdeskResult();

        log.info("Searching '{}' {}", phrase, searchQuery);
        final HelpdeskMessage result = esTemplate.query(searchQuery, sr -> {
            log.info("Got {} hits", sr.getHits().totalHits());
            final HelpdeskMessage msg = new HelpdeskMessage();
            msg.setInputText(phrase);
            for (final SearchHit hit : sr.getHits()) {
                log.info("Hit {}: {}", hit.getScore(), hit.getSource());
                if (hit.getScore() >= THRESHOLD) {
                    final String trackingId = (String) hit.getSource().get("TrackingID");
                    final String caseTitle = ((String) hit.getSource().get("JudulLaporan")).trim();
                    msg.setResponseText(String.format("Silakan dukung @LAPOR1708 #%s %s",
                            trackingId, caseTitle, 80));
                    msg.setGmapsUri(String.format("https://www.lapor.go.id/id/%s/", trackingId));
                    log.info("Responding (score={}) {}", hit.getScore(), msg);
                    break;
                }
            }
            return msg;
        });

//        result.setElapsedMillis((int) (System.currentTimeMillis() - startTime));
        return result;
    }

}
