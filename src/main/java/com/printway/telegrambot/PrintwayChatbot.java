package com.printway.telegrambot;

import com.printway.service.NewOrderService;
import com.printway.service.NewTrackingService;
import org.apache.log4j.Logger;
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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class PrintwayChatbot extends TelegramLongPollingBot {
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

    private final Logger logger = Logger.getLogger(this.getClass());
    private String token = "1613155455:AAG1WJ3hxXehq9DtX1-hk8MpAc0mYweR5eg";
    private String username = "printwayioBot";
    private final String TECHROOM_GROUP_CHAT_ID = "-1001236402745";
    private final String TRACKING_GROUP_CHAT_ID = "-1001276180425";

    @PostConstruct
    public void registerBot() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            token = "1718882712:AAFa8WMH6T176FG5omdNwmXW8on-ejXMenI";
            username = "printwayadminbot";
            botsApi.registerBot(this);
            logger.error(e);
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
                            "Hello! Have a good day!"
                                    + "\n- Name: @" + message.getFrom().getUserName());
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
                        logger.error(e);
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
            logger.error(e);
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
            logger.error(e);
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
            logger.error(e);
        }
    }

    public void SendMessageWithInput(String chatId, Date dateEntered) {
        SendMessageWithInputADayDelay(chatId, dateEntered);
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("E dd/MM/yyyy");
        SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateEnteredForm = dateEnteredFormat.format(dateEntered);
        String dateDisplay = formatter1.format(dateEntered);
        String currentDateDisplay = formatter2.format(dateEntered);
        Form(chatId, dateEnteredForm, dateDisplay, currentDateDisplay);
    }

    public void SendMessageWithInputADayDelay(String chatId, Date dateEntered) {
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
    }

    public void SendStatisticAuto(String chatId) {
        Date currentDate = new Date(System.currentTimeMillis());
        SendStatisticAutoADayDelay(chatId, currentDate);
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss, E dd/MM/yyyy");
        SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateEnteredForm = dateEnteredFormat.format(new Date(System.currentTimeMillis()));
        String dateDisplay = formatter1.format(currentDate);
        String currentDateDisplay = formatter2.format(currentDate);
        Form(chatId, dateEnteredForm, dateDisplay, currentDateDisplay);
    }

    public void SendStatisticAutoADayDelay(String chatId, Date currentDate) {
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
    }

    public void FindNewestOrder(String chatId) {
        Date dateCurrent = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCurrent);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        Date oneHourBack = calendar.getTime();
        SimpleDateFormat dateTimeDatabaseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateCurrentString = dateTimeDatabaseFormat.format(dateCurrent);
        String dateOneHourBackString = dateTimeDatabaseFormat.format(oneHourBack);
        List<Integer> newOrderList = newOrderService.findNewestOrder(dateOneHourBackString, dateCurrentString);
        BigDecimal totalNewestOrder;
        BigDecimal zero = new BigDecimal(0);
        BigDecimal test = new BigDecimal(String.valueOf(newOrderList.get(0)));
        if (newOrderList.get(0) != null && !test.equals(zero)) {
            totalNewestOrder = new BigDecimal(String.valueOf(newOrderList.get(0)));
            String textFormatHasOrder = "*** <u><b>THÔNG BÁO "
                    + dateCurrentString
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
        } else {
            logger.info("Don't Have Any New Orders!");
        }
    }

    public void SendCountErrorTracking(String chatId) {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss, E dd/MM/yyyy");
        String dateDisplay = formatter1.format(currentDate);
        String currentDateDisplay = formatter2.format(currentDate);
        List<Integer> errorTrackingQuantity = newTrackingService.countErrorTracking();
        BigDecimal totalErrorTracking;
        BigDecimal zero = new BigDecimal(0);
        BigDecimal test = new BigDecimal(String.valueOf(errorTrackingQuantity.get(0)));
        if (errorTrackingQuantity.get(0) != null && !test.equals(zero)) {
            totalErrorTracking = new BigDecimal(String.valueOf(errorTrackingQuantity.get(0)));
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
                            + totalErrorTracking
                            + "</b>"
                            + " Tracking Info Error(s) Cần Update!"
            );
        } else {
            logger.info("Don't Have Any Tracking Info Errors Need Update!");
        }
    }

    public void Form(String chatId, String dateEnteredForm, String dateDisplay, String currentDateDisplay) {
        List<Integer> printQuantity = newOrderService.countPrintQuantity(dateEnteredForm);
        List<Integer> printedQuantity = newOrderService.countPrintedQuantity(dateEnteredForm);
        List<Integer> packagedQuantity = newOrderService.countPackagedQuantity(dateEnteredForm);
        List<Integer> errorQuantityIn = newOrderService.countErrorQuantityIn(dateEnteredForm);
        List<Integer> totalOrder = newOrderService.countOrder(dateEnteredForm);
        List<Integer> totalLabeled = newTrackingService.countLabeled(dateEnteredForm);
        List<Integer> totalShipped = newTrackingService.countShipped(dateEnteredForm);
        BigDecimal orders = new BigDecimal(String.valueOf(totalOrder.get(0)));
        BigDecimal shippeds = new BigDecimal(String.valueOf(totalShipped.get(0)));
        BigDecimal labeleds = new BigDecimal(String.valueOf(totalLabeled.get(0)));
        BigDecimal totalUnshipped = orders.subtract(shippeds);
        BigDecimal totalUnlabeled = orders.subtract(labeleds);
        BigDecimal print;
        BigDecimal printed;
        BigDecimal packaged;
        BigDecimal error;
        BigDecimal order;
        BigDecimal labeled;
        BigDecimal unlabeled;
        BigDecimal shipped;
        BigDecimal unshipped;
        if (printQuantity.size() == 0 || printQuantity.get(0) == null) {
            print = new BigDecimal(0);
        } else {
            print = new BigDecimal(String.valueOf(printQuantity.get(0)));
        }
        if (printedQuantity.size() == 0 || printedQuantity.get(0) == null) {
            printed = new BigDecimal(0);
        } else {
            printed = new BigDecimal(String.valueOf(printedQuantity.get(0)));
        }
        if (packagedQuantity.size() == 0 || packagedQuantity.get(0) == null) {
            packaged = new BigDecimal(0);
        } else {
            packaged = new BigDecimal(String.valueOf(packagedQuantity.get(0)));
        }
        if (errorQuantityIn.size() == 0 || errorQuantityIn.get(0) == null) {
            error = new BigDecimal(0);
        } else {
            error = new BigDecimal(String.valueOf(errorQuantityIn.get(0)));
        }
        if (totalOrder.size() == 0 || totalOrder.get(0) == null) {
            order = new BigDecimal(0);
        } else {
            order = new BigDecimal(String.valueOf(totalOrder.get(0)));
        }
        if (totalLabeled.size() == 0 || totalLabeled.get(0) == null) {
            labeled = new BigDecimal(0);
        } else {
            labeled = new BigDecimal(String.valueOf(totalLabeled.get(0)));
        }
        if (totalShipped.size() == 0 || totalShipped.get(0) == null) {
            shipped = new BigDecimal(0);
        } else {
            shipped = new BigDecimal(String.valueOf(totalShipped.get(0)));
        }
        if (totalUnlabeled.equals(new BigDecimal(0))) {
            unlabeled = new BigDecimal(0);
        } else {
            unlabeled = new BigDecimal(String.valueOf(totalUnlabeled));
        }
        if (totalUnlabeled.equals(new BigDecimal(0))) {
            unshipped = new BigDecimal(0);
        } else {
            unshipped = new BigDecimal(String.valueOf(totalUnshipped));
        }
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
    }
}
