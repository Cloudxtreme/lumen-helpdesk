package org.lskk.lumen.helpdesk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayDeque;
import java.util.function.Consumer;

/**
 * Created by ceefour on 23/06/2016.
 */
public class JakartaCityBot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(JakartaCityBot.class);

    @Inject
    private Environment env;

    final ArrayDeque<Consumer<String>> consumers = new ArrayDeque<>();

    public void ask(String question, Consumer<String> callback) throws TelegramApiException {
        final String staffChatId = env.getRequiredProperty("helpdesk.telegram.staff.chat-id");
        final SendMessage replySend = new SendMessage();
        replySend.setChatId(staffChatId);
        replySend.setText(question);
        sendMessage(replySend);
        consumers.add(callback);
    }

    @Override
    public void onUpdateReceived(Update update) {
        final String staffChatId = env.getProperty("helpdesk.telegram.staff.chat-id");
        log.info("Update {}. Message={}", update, update.getMessage());
        if (null != update.getMessage() && null != update.getMessage().getText()) {
            log.info("Chat ID={} from {}. Message.text: {}", update.getMessage().getChatId(),
                    update.getMessage().getFrom(), update.getMessage().getText());

            log.info("StaffChatID={} message chat id={} consumers={}", staffChatId, update.getMessage().getChatId(), consumers.size());
            if (null != staffChatId && Long.valueOf(staffChatId).equals(update.getMessage().getChatId()) && !consumers.isEmpty()) {
                log.info("Received answer for previous escalation: {}", update.getMessage().getText());
                final Consumer<String> poll = consumers.poll();
                poll.accept(update.getMessage().getText());
            } else {
                log.info("Regular chatting: {}", update.getMessage().getText());
                final String normalized = update.getMessage().getText().toLowerCase();

                try {
                    if (normalized.contains("mrt")) {
                        final SendPhoto replySend = new SendPhoto();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setNewPhoto(new File("images/mrt-jakarta.jpg"));
                        replySend.setCaption(update.getMessage().getFrom().getFirstName() + ", MRT Jakarta dijadwalkan mulai beroperasi pada awal tahun 2019. Wah, Bang SOLeh dan Mpok LUSI ga sabar rasanya! \uD83D\uDE01");
                        sendPhoto(replySend);
                    } else if (normalized.contains("skdu")) {
                        final SendPhoto replySend = new SendPhoto();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setNewPhoto(new File("images/skdu.jpg"));
                        replySend.setCaption(update.getMessage().getFrom().getFirstName() + "... kini membuat SKDU semakin simple! Tidak perlu ada survey sehingga tdk perlu merujuk kepada zonasi. Silakan klik link https://t.co/VYYgNdDmWe ya \uD83D\uDE01 #MelayaniJakarta");
                        sendPhoto(replySend);

                        //                    final SendMessage replySend = new SendMessage();
                        //                    replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        //                    replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        //                    replySend.setText(update.getMessage().getFrom().getFirstName() + ", silakan klik link https://t.co/VYYgNdDmWe ya :)");
                        //                    sendMessage(replySend);
                    } else if (normalized.contains("domisili")) {
                        final SendMessage replySend = new SendMessage();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setText(update.getMessage().getFrom().getFirstName() + ", betul. Hanya saja aturannya diperbaharui sehingga memudahkan pemohon yang ingin membuat SKDU.");
                        sendMessage(replySend);
                    } else if (normalized.contains("izin")) {
                        final SendPhoto replySend = new SendPhoto();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setNewPhoto(new File("images/izin-usaha.jpg"));
                        replySend.setCaption(update.getMessage().getFrom().getFirstName() + ", ini alasannya kenapa harus memiliki izin usaha.");
                        sendPhoto(replySend);
                    } else if (normalized.matches(".*(bptsp|layanan).*")) {
                        final SendPhoto replySend = new SendPhoto();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setNewPhoto(new File("images/bptsp.jpg"));
                        replySend.setCaption(update.getMessage().getFrom().getFirstName() + ".. Pemprov DKI Jakarta terus melakukan inovasi, salah satunya melalui BPTSP #MelayaniJakarta");
                        sendPhoto(replySend);
                    } else if (normalized.matches(".*(hebat|kasih|terima kasih|trmksh|terimakasih|bagus|keren|makasih).*")) {
                        final SendMessage replySend = new SendMessage();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setText("Terima kasih " + update.getMessage().getFrom().getFirstName() + " \uD83D\uDE01. Kami selalu berusaha meningkatkan pelayanan.");
                        sendMessage(replySend);
                    } else if (normalized.matches(".*(jelek|parah|gimana sih|capek|ancur|sebel|sebal|bete|nyebelin).*")) {
                        final SendMessage replySend = new SendMessage();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setText("Mohon maaf " + update.getMessage().getFrom().getFirstName() + " atas ketidaknyamanannya. \uD83D\uDE14");
                        sendMessage(replySend);
                    } else if (normalized.matches(".*(halo|helo|hello|hai|hallo|selamat).*")) {
                        final SendMessage replySend = new SendMessage();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setText(update.getMessage().getFrom().getFirstName() + ", ada yang dapat kami bantu? \uD83D\uDE01");
                        sendMessage(replySend);
                    } else {
                        final SendMessage replySend = new SendMessage();
                        replySend.setChatId(String.valueOf(update.getMessage().getChatId()));
                        replySend.setReplayToMessageId(update.getMessage().getMessageId());
                        replySend.setText(update.getMessage().getFrom().getFirstName() + "... Thanks, " + update.getMessage().getText());
                        sendMessage(replySend);
                    }
                } catch (TelegramApiException e) {
                    log.error("Cannot send reply", e);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "jakartacitybot";
    }

    @Override
    public String getBotToken() {
        return env.getRequiredProperty("telegram.jakartacitybot.token");
    }
}
