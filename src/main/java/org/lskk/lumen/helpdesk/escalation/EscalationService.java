package org.lskk.lumen.helpdesk.escalation;

import org.apache.commons.lang3.StringUtils;
import org.lskk.lumen.helpdesk.JakartaCityBot;
import org.lskk.lumen.helpdesk.submit.HelpdeskMessage;
import org.lskk.lumen.helpdesk.submit.InputKind;
import org.lskk.lumen.helpdesk.twitter.TwitterHelpdeskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.TelegramApiException;
import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import java.util.ArrayDeque;

/**
 * Created by ceefour on 02/08/2016.
 */
@Service
public class EscalationService {

    private static Logger log = LoggerFactory.getLogger(EscalationService.class);

    @Autowired(required = false)
    private JakartaCityBot jakartaCityBot;
    @Autowired(required = false)
    private TwitterHelpdeskConfig twitterHelpdeskConfig;

    private ArrayDeque<HelpdeskMessage> messageQueue = new ArrayDeque<>();

    public void receiveUnhandledMessage(HelpdeskMessage helpdeskMessage) {
        messageQueue.add(helpdeskMessage);
        pollIfPossible();
    }

    /**
     * Poll next input in queue, only if it's not yet being processed.
     */
    protected void pollIfPossible() {
        final HelpdeskMessage head = messageQueue.peek();
        if (null == head.getEscalationState() || HelpdeskMessage.EscalationState.NONE == head.getEscalationState()) {
            askStaff(head);
        }
    }

    protected void askStaff(HelpdeskMessage head) {
        if (null != jakartaCityBot) {
            head.setInputKind(InputKind.ESCALATION);
            head.setEscalationState(HelpdeskMessage.EscalationState.ASKED);
            log.info("Asking staff for {}", head);
            try {
                jakartaCityBot.ask("@" + head.getChannelSenderScreenName() + ": "+ head.getInputText(),
                        answer -> {
                            log.info("Got answer '{}' for {}", answer, head);
                            head.setResponseText(answer);
                            head.setEscalationState(HelpdeskMessage.EscalationState.ANSWERED);
                            messageQueue.remove(head);

                            if (null != twitterHelpdeskConfig) {
                                if (null != head.getChannelSenderScreenName()) {
                                    String tweetText = "@" + head.getChannelSenderScreenName() + " " + answer;
                                    tweetText = StringUtils.abbreviate(tweetText, 115);
//                                if (null != msg.getGmapsUri()) {
//                                    tweetText += " " + msg.getGmapsUri();
//                                }
                                    final StatusUpdate responseTweet = new StatusUpdate(tweetText);
//                                responseTweet.setInReplyToStatusId(status.getId());
//                                if (null != msg.getHospitalLat() && null != msg.getHospitalLon()) {
//                                    responseTweet.setLocation(new GeoLocation(msg.getHospitalLat(), msg.getHospitalLon()));
//                                    responseTweet.setDisplayCoordinates(true);
//                                }
                                    log.info("Responding Twitter {}", responseTweet);
                                    try {
                                        twitterHelpdeskConfig.updateStatus(responseTweet);
                                    } catch (TwitterException e) {
                                        log.error("Cannot tweet " + responseTweet, e);
                                    }
                                } else {
                                    log.info("no channelSenderScreenName found: {}", head);
                                }
                            } else {
                                log.warn("Cannot reply because twitterHelpdeskConfig bean is not active");
                            }

                        });
            } catch (TelegramApiException e) {
                log.error("Error asking staff for " + head, e);
            }
        } else {
            log.info("JakartaCityBot bean is not loaded, cannot ask staff for {}", head);
        }
    }

}
