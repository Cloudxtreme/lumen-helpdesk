package org.lskk.lumen.helpdesk.submit;

import com.google.common.collect.Iterables;
import org.lskk.lumen.helpdesk.escalation.EscalationService;
import org.lskk.lumen.helpdesk.jsc.JscService;
import org.lskk.lumen.helpdesk.lapor.LaporService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Asks both {@link org.lskk.lumen.helpdesk.jsc.JscService} and {@link org.lskk.lumen.helpdesk.lapor.LaporService}.
 * Created by ceefour on 02/08/2016.
 */
@Service
public class SubmitService {

    private static Logger log = LoggerFactory.getLogger(SubmitService.class);

    @Inject
    private JscService jscService;
    @Inject
    private LaporService laporService;
    @Inject
    private EscalationService escalationService;

    public HelpdeskResult submit(HelpdeskInput input) {
        final long startTime = System.currentTimeMillis();

        final HelpdeskResult jscResult = jscService.submit(input);
        final HelpdeskResult laporResult = laporService.search(input);

        // Prioritize JSC result if possible
        final HelpdeskResult helpdeskResult = jscResult;
        for (int i = 0; i < helpdeskResult.getMessages().size(); i++) {
            if (null == helpdeskResult.getMessages().get(i).getResponseText() && null != laporResult.getMessages().get(i).getResponseText()) {
                helpdeskResult.getMessages().set(i, laporResult.getMessages().get(i));
            }
        }

        @Nullable
        final HelpdeskMessage first = Iterables.getFirst(helpdeskResult.getMessages(), null);
        log.info("First message response: {}", first);
        if (null != first && null == first.getResponseText()) {
            escalationService.receiveUnhandledMessage(first);
        }

        final int elapsedMillis = (int) (System.currentTimeMillis() - startTime);
        helpdeskResult.setElapsedMillis(elapsedMillis);
        return helpdeskResult;
    }

}
