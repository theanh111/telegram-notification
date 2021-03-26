package com.printway.telegrambot;

import com.printway.service.NewOrderService;
import com.printway.service.NewTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Component
@EnableScheduling
public class PrintwayChatbot extends TelegramLongPollingBot {
    private String token = "1613155455:AAG1WJ3hxXehq9DtX1-hk8MpAc0mYweR5eg";
    private String username = "printwayioBot";
    private final String TECHROOM_GROUP_CHAT_ID = "-1001236402745";
    private final String TRACKING_GROUP_CHAT_ID = "-1001276180425";

    @Scheduled(cron = " 0 0 * ? * *")
    public void autoFindNewestOrderEveryHour() {
        FindNewestOrder(TECHROOM_GROUP_CHAT_ID);
        FindNewestOrder(TRACKING_GROUP_CHAT_ID);
    }

    @Scheduled(cron = "0 30 17 ? * *")
    public void autoSendStatistic() {
        SendStatisticAuto(TECHROOM_GROUP_CHAT_ID);
        SendStatisticAuto(TRACKING_GROUP_CHAT_ID);
    }

    @Scheduled(cron = "0 30 8 ? * *")
    public void scheduleCountErrorTracking8AM() {
        SendCountErrorTracking(TECHROOM_GROUP_CHAT_ID);
        SendCountErrorTracking(TRACKING_GROUP_CHAT_ID);
    }

    @Scheduled(cron = "0 0 14 ? * *")
    public void scheduleCountErrorTracking2PM() {
        SendCountErrorTracking(TECHROOM_GROUP_CHAT_ID);
        SendCountErrorTracking(TRACKING_GROUP_CHAT_ID);
    }

    @PostConstruct
    public void registerBot() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            token = "1718882712:AAFa8WMH6T176FG5omdNwmXW8on-ejXMenI";
            username = "printwayadminbot";
            botsApi.registerBot(this);
        }
    }

    @Autowired
    private NewOrderService newOrderService;
    @Autowired
    private NewTrackingService newTrackingService;

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
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                String msg = message.getText().toLowerCase();
                if (msg.contains("hello")) {
                    SendMessage(message,
                            "Hello! Have a good day!");
                }
                if (msg.equals("about")) {
                    SendMessage(message,
                            "CÔNG TY TNHH PRINTWAY (PRINTWAY FACTORY)"
                                    + "\n- About Us: https://printway.io");
                }
                if (msg.equals("tracking") && message.getFrom().getId().equals(1631693223)) {
                    SendCountErrorTracking(message.getChatId().toString());
                }
                if (msg.equals("auto") && message.getFrom().getId().equals(1631693223)) {
                    SendStatisticAuto(message.getChatId().toString());
                }
                if (msg.equals("newest") && message.getFrom().getId().equals(1631693223)) {
                    FindNewestOrder(message.getChatId().toString());
                }
                if (message.getText().contains("2021-") && message.getFrom().getId().equals(1631693223)) {
                    try {
                        String dateEnter = message.getText();
                        SimpleDateFormat dateReceivedFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateEntered = dateReceivedFormat.parse(dateEnter);
                        SendMessageWithInput(message.getChatId().toString(), dateEntered);
                    } catch (ParseException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }
    }

    public void SendMessage(Message message, String text) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setText(text);
            sendMessage.setParseMode("HTML");
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
        }
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

    public void SendMessageWithInput(String chatId, Date dateEntered) {
        try {
            SendMessageWithInputADayDelay(chatId, dateEntered);
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("E dd/MM/yyyy");
            SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateEnteredForm = dateEnteredFormat.format(dateEntered);
            String dateDisplay = formatter1.format(dateEntered);
            String currentDateDisplay = formatter2.format(dateEntered);
            Form(chatId, dateEnteredForm, dateDisplay, currentDateDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendMessageWithInputADayDelay(String chatId, Date dateEntered) {
        try {
            LocalDate localDate = dateEntered.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
