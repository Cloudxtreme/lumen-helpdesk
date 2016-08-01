package org.lskk.lumen.helpdesk.lapor;

import org.lskk.lumen.helpdesk.submit.HelpdeskInput;
import org.lskk.lumen.helpdesk.submit.HelpdeskMessage;
import org.lskk.lumen.helpdesk.submit.HelpdeskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * REST Controller to search LAPOR.
 * Created by ceefour on 01/08/2016.
 */
@RestController @RequestMapping("lapor")
public class LaporController {

    private static Logger log = LoggerFactory.getLogger(LaporController.class);

    @Inject
    private LaporService laporService;

    @RequestMapping(path = "search", method = RequestMethod.GET)
    public HelpdeskMessage search(@RequestParam("q") String phrase) {
        return laporService.search(phrase);
    }

    @RequestMapping(path = "helpdesk", method = RequestMethod.POST)
    public HelpdeskResult helpdesk(@RequestBody HelpdeskInput helpdeskInput) {
        return laporService.search(helpdeskInput);
    }

}
