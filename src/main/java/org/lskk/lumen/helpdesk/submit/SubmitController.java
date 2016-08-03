package org.lskk.lumen.helpdesk.submit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * REST Controller to submit helpdesk job, e.g. incoming message.
 * Created by ceefour on 01/08/2016.
 */
@RestController
public class SubmitController {

    private static Logger log = LoggerFactory.getLogger(SubmitController.class);

    @Inject
    private SubmitService submitService;

    @RequestMapping(path = "submit", method = RequestMethod.POST)
    public HelpdeskResult submit(@RequestBody HelpdeskInput input) {
        return submitService.submit(input);
    }

}
