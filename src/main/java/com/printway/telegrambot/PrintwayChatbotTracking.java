package com.printway.telegrambot;

import com.printway.service.NewOrderService;
import com.printway.service.NewTrackingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class PrintwayChatbotTracking extends TelegramLongPollingBot {
    public void runThis() {
        autoFindNewestOrderEveryHour();
        countErrorTracking();
    }

    private final String TECHROOM_GROUP_CHAT_ID = new PrintwayChatbotConfig().getTECHROOM_GROUP_CHAT_ID();
    private final String TRACKING_GROUP_CHAT_ID = new PrintwayChatbotConfig().getTRACKING_GROUP_CHAT_ID();

    private NewOrderService newOrderService = new NewOrderService();
    private NewTrackingService newTrackingService = new NewTrackingService();

    public void autoFindNewestOrderEveryHour() {
        FindNewestOrder(TECHROOM_GROUP_CHAT_ID);
        FindNewestOrder(TRACKING_GROUP_CHAT_ID);
    }

    public void countErrorTracking() {
        SendCountErrorTracking(TECHROOM_GROUP_CHAT_ID);
        SendCountErrorTracking(TRACKING_GROUP_CHAT_ID);
    }

    @Override
    public String getBotUsername() {
        return new PrintwayChatbotConfig().getUsername();
    }

    @Override
    public String getBotToken() {
        return new PrintwayChatbotConfig().getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void SendNewestOrder(String chatId, String text) {
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

    public void FindNewestOrder(String chatId) {
        try {
            Date dateCurrent = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateCurrent);
            calendar.add(Calendar.HOUR_OF_DAY, -1);
            Date oneHourBack = calendar.getTime();
            SimpleDateFormat dateTimeDatabaseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            String dateDisplay = formatter1.format(dateCurrent);
            String dateCurrentString = dateTimeDatabaseFormat.format(dateCurrent);
            String dateOneHourBackString = dateTimeDatabaseFormat.format(oneHourBack);
            Integer totalNewestOrder = newOrderService.findNewestOrder(dateOneHourBackString, dateCurrentString);
            if (totalNewestOrder != null && totalNewestOrder > 0) {
                String textFormatHasOrder = "*** <u><b>THÔNG BÁO "
                        + dateDisplay
                        + "</b></u> ***"
                        + "\n("
                        + "<code>"
                        + dateOneHourBackString
                        + " đến "
                        + dateCurrentString
                        + "</code>"
                        + ")"
                        + "\n-------------------------"
                        + "\nCó "
                        + "<b>"
                        + totalNewestOrder
                        + "</b>"
                        + " New Order(s)";
                SendNewestOrder(chatId, textFormatHasOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
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