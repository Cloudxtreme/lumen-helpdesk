package org.lskk.lumen.helpdesk.lapor;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.lskk.lumen.helpdesk.submit.HelpdeskInput;
import org.lskk.lumen.helpdesk.submit.HelpdeskMessage;
import org.lskk.lumen.helpdesk.submit.HelpdeskResult;
import org.lskk.lumen.helpdesk.submit.SubmitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * REST Controller to search LAPOR.
 * Created by ceefour on 01/08/2016.
 */
@RestController @RequestMapping("lapor")
public class LaporController {

    private static Logger log = LoggerFactory.getLogger(LaporController.class);

//    @Inject
//    private LaporCaseRepository laporCaseRepo;
    @Inject
    private ElasticsearchOperations esTemplate;

    @RequestMapping(path = "search", method = RequestMethod.GET)
    public HelpdeskResult search(@RequestParam("q") String phrase) {
        final long startTime = System.currentTimeMillis();
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("logstash-2016.07.27")
                .withQuery(QueryBuilders.matchQuery("_all", phrase))
                .build();
//        laporCaseRepo.search(searchQuery);

        final HelpdeskResult result = new HelpdeskResult();

        log.info("Searching '{}' {}", phrase, searchQuery);
        final Void dummy = esTemplate.query(searchQuery, sr -> {
            log.info("Got {} hits", sr.getHits().totalHits());
            for (final SearchHit hit : sr.getHits()) {
                log.info("Hit {}: {}", hit.getScore(), hit);
                if (hit.getScore() >= 5f) {
                    final HelpdeskMessage msg = new HelpdeskMessage();
                    msg.setInputText(phrase);
                    final String trackingId = (String) hit.getSource().get("TrackingID");
                    final String caseTitle = (String) hit.getSource().get("JudulLaporan");
                    msg.setResponseText(String.format("Silakan dukung @LAPOR1708 #%s %s https://www.lapor.go.id/id/%s/",
                            trackingId, StringUtils.abbreviate(caseTitle, 80), trackingId));
                    log.info("Responding (score={}) {}", hit.getScore(), msg);
                    result.getMessages().add(msg);
                    break;
                }
            }
            return null;
        });

        result.setElapsedMillis((int) (System.currentTimeMillis() - startTime));
        return result;
    }

}
