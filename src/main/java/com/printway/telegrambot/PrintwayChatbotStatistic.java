package com.printway.telegrambot;

import com.printway.service.NewOrderService;
import com.printway.service.NewTrackingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class PrintwayChatbotStatistic extends TelegramLongPollingBot {
    public void runThis() {
        autoSendStatistic();
    }

    private final String TECHROOM_GROUP_CHAT_ID = new PrintwayChatbotConfig().getTECHROOM_GROUP_CHAT_ID();
    private final String TRACKING_GROUP_CHAT_ID = new PrintwayChatbotConfig().getTRACKING_GROUP_CHAT_ID();

    private NewOrderService newOrderService = new NewOrderService();
    private NewTrackingService newTrackingService = new NewTrackingService();

    public void autoSendStatistic() {
        SendStatisticAuto(TECHROOM_GROUP_CHAT_ID);
        SendStatisticAuto(TRACKING_GROUP_CHAT_ID);
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

    public void SendStatisticAuto(String chatId) {
        try {
            Date currentDate = new Date(System.currentTimeMillis());
            SendStatisticAutoADayDelay(chatId, currentDate);
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss, E dd/MM/yyyy");
            SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateEnteredForm = dateEnteredFormat.format(new Date(System.currentTimeMillis()));
            String dateDisplay = formatter1.format(currentDate);
            String currentDateDisplay = formatter2.format(currentDate);
            Form(chatId, dateEnteredForm, dateDisplay, currentDateDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendStatisticAutoADayDelay(String chatId, Date currentDate) {
        try {
            LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate aDayDelay = localDate.minusDays(1);
            Date dateDelay = Date.from(aDayDelay.atStartOfDay(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("E dd/MM/yyyy");
            SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateEnteredForm = dateEnteredFormat.format(dateDelay);
            String dateDisplay = formatter1.format(dateDelay);
            String currentDateDisplay = formatter2.format(dateDelay);
            Form(chatId, dateEnteredForm, dateDisplay, currentDateDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Form(String chatId, String dateEnteredForm, String dateDisplay, String currentDateDisplay) {
        try {
            Integer print = newOrderService.countPrintQuantity(dateEnteredForm);
            Integer printed = newOrderService.countPrintedQuantity(dateEnteredForm);
            Integer packaged = newOrderService.countPackagedQuantity(dateEnteredForm);
            Integer error = newOrderService.countErrorQuantityIn(dateEnteredForm);
            Integer order = newOrderService.countOrder(dateEnteredForm);
            Integer labeled = newTrackingService.countLabeled(dateEnteredForm);
            Integer shipped = newTrackingService.countShipped(dateEnteredForm);
            Integer unshipped = order - shipped;
            Integer unlabeled = order - labeled;
            SendMessageGroup(chatId,
                    "*** <u><b>THỐNG KÊ "
                            + dateDisplay
                            + "</b></u> ***"
                            + "\n("
                            + "<code>"
                            + currentDateDisplay
                            + "</code>"
                            + ")"
                            + "\n-------------------------"
                            + "\nTotal Print: "
                            + "<b>"
                            + print
                            + "</b>"
                            + "\nPrinted: "
                            + "<b>"
                            + printed
                            + "</b>"
                            + "\nPackaged: "
                            + "<b>"
                            + packaged
                            + "</b>"
                            + "\nError: "
                            + "<b>"
                            + error
                            + "</b>"
                            + "\n-------------------------"
                            + "\nTotal Order: "
                            + "<b>"
                            + order
                            + "</b>"
                            + "\nLabeled: "
                            + "<b>"
                            + labeled
                            + "</b>"
                            + "\nUnlabeled: "
                            + "<b>"
                            + unlabeled
                            + "</b>"
                            + "\nShipped: "
                            + "<b>"
                            + shipped
                            + "</b>"
                            + "\nUnshipped: "
                            + "<b>"
                            + unshipped
                            + "</b>"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}