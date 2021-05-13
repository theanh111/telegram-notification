package com.printway.telegrambot;

import com.printway.service.NewOrderService;
import com.printway.service.NewTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Component

public class PrintwayChatbotCountError extends TelegramLongPollingBot {
    public void runThis() {
        SendCountErrorTracking(TRACKING_GROUP_CHAT_ID);
        System.out.println("Bot running count error...");
    }

    private String token = "1718882712:AAFa8WMH6T176FG5omdNwmXW8on-ejXMenI";
    private String username = "printwayadminbot";
    private final String TECHROOM_GROUP_CHAT_ID = "-1001236402745";
    private final String TRACKING_GROUP_CHAT_ID = "-1001276180425";
    private final String TEST_GROUP_CHAT_ID = "-1001391001050";

    private NewOrderService newOrderService = new NewOrderService();
    private NewTrackingService newTrackingService = new NewTrackingService();

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void SendMessageGroup(String chatId, String text) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(text);
            sendMessage.setParseMode("HTML");
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
        }
    }

    public void SendCountErrorTracking(String chatId) {
        try {
            Date currentDate = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss, E dd/MM/yyyy");
            String dateDisplay = formatter1.format(currentDate);
            String currentDateDisplay = formatter2.format(currentDate);
            Integer errorTrackingQuantity = newTrackingService.countErrorTracking();
            if (errorTrackingQuantity != null && errorTrackingQuantity > 0) {
                SendMessageGroup(chatId,
                        "*** <u><b>THÔNG BÁO "
                                + dateDisplay
                                + "</b></u> ***"
                                + "\n("
                                + "<code>"
                                + currentDateDisplay
                                + "</code>"
                                + ")"
                                + "\n-------------------------"
                                + "\nCó "
                                + "<b>"
                                + errorTrackingQuantity
                                + "</b>"
                                + " Tracking Info Error(s) Cần Update!"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}