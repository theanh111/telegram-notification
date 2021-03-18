package com.printway.telegrambot;

import com.printway.service.NewOrderService;
import com.printway.service.NewTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class PrintwayChatbot extends TelegramLongPollingBot {
    @Scheduled(cron = "0 30 17 ? * *")
    public void run() throws ParseException {
        SendAuto();
    }

//    private String username = "printwaytestbot";
//    private String token = "1764851749:AAEDunHSDiNAagUngLqqvKJMzP7KN6dAuEM";

    private String token = "1613155455:AAG1WJ3hxXehq9DtX1-hk8MpAc0mYweR5eg";
    private String username = "printwayioBot";

//    private String token = "1718882712:AAFa8WMH6T176FG5omdNwmXW8on-ejXMenI";
//    private String username = "printwayadminbot";

    private final String TEST1_GROUP_CHAT_ID = "-1001391001050";
    private final String TEST2_GROUP_CHAT_ID = "-1001300395182";
    private final String TECHROOM_GROUP_CHAT_ID = "-1001236402745";
    private final String KIEN_CHAT_ID = "1231177515";
    private final String TUAN_CHAT_ID = "967867808";
    private final String THEANH_CHAT_ID = "1631693223";
    private final String THANG_CHAT_ID = "1325472968";

    private String CHAT_ID = THEANH_CHAT_ID;
    private String CHAT_ID2 = TEST1_GROUP_CHAT_ID;

    @PostConstruct
    public void registerBot() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            BotSession botSession = botsApi.registerBot(this);
            SendAutoADayDelay();
            SendAuto();
        } catch (TelegramApiException e) {
            token = "1718882712:AAFa8WMH6T176FG5omdNwmXW8on-ejXMenI";
            username = "printwayadminbot";
            botsApi.registerBot(this);
            e.printStackTrace();
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
                if (msg.equals("hello")) {
                    SendMessage(message,
                            "Hello!"
                                    + "\n- Chat ID: " + message.getChatId().toString()
                                    + "\n- Name: @" + message.getFrom().getUserName());
                }
                if (msg.equals("about")) {
                    SendMessage(message,
                            "CÔNG TY TNHH PRINTWAY (PRINTWAY FACTORY)"
                                    + "\n- About Us: https://printway.io");
                }
                if (msg.equals("test") && message.getFrom().getId().equals(1631693223)) {
                    SendAutoADayDelay();
                    SendAuto();
                }
                if (message.getText().contains("2021-")) {
                    try {
                        String dateEnter = message.getText();
                        SimpleDateFormat dateReceivedFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateEntered = dateReceivedFormat.parse(dateEnter);
                        try {
                            SendMessageWithInputADayDelay(dateEntered);
                            SendMessageWithInput(dateEntered);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
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
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void SendMessageGroup(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setParseMode("HTML");
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendMessageWithInput(Date dateEntered) throws Exception {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss, E dd/MM/yyyy");

            SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dateEnteredForm = dateEnteredFormat.format(dateEntered);
            System.out.println("dateEnteredForm: " + dateEnteredForm);

            String dateDisplay = formatter1.format(dateEntered);
            String currentDateDisplay = formatter2.format(dateEntered);

            List<Integer> printQuantity = newOrderService.countPrintQuantity(dateEnteredForm);
            List<Integer> printedQuantity = newOrderService.countPrintedQuantity(dateEnteredForm);
            List<Integer> packagedQuantity = newOrderService.countPackagedQuantity(dateEnteredForm);
            List<Integer> errorQuantityIn = newOrderService.countErrorQuantityIn(dateEnteredForm);
            List<Integer> totalOrder = newOrderService.countOrder(dateEnteredForm);

            List<Integer> totalLabeled = newTrackingService.countLabeled(dateEnteredForm);
            List<Integer> totalUnlabeled = newTrackingService.countUnlabeled(dateEnteredForm);
            List<Integer> totalShipped = newTrackingService.countShipped(dateEnteredForm);
            List<Integer> totalUnshipped = newTrackingService.countUnshipped(dateEnteredForm);

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

            if (totalUnlabeled.size() == 0 || totalUnlabeled.get(0) == null) {
                unlabeled = new BigDecimal(0);
            } else {
                unlabeled = new BigDecimal(String.valueOf(totalUnlabeled.get(0)));
            }

            if (totalShipped.size() == 0 || totalShipped.get(0) == null) {
                shipped = new BigDecimal(0);
            } else {
                shipped = new BigDecimal(String.valueOf(totalShipped.get(0)));
            }

            if (totalUnshipped.size() == 0 || totalUnshipped.get(0) == null) {
                unshipped = new BigDecimal(0);
            } else {
                unshipped = new BigDecimal(String.valueOf(totalUnshipped.get(0)));
            }

            SendMessageGroup(CHAT_ID,
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

    public void SendMessageWithInputADayDelay(Date dateEntered) throws Exception {
        try {
            LocalDate localDate = dateEntered.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate aDayDelay = localDate.minusDays(1);
            Date dateDelay = Date.from(aDayDelay.atStartOfDay(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("E dd/MM/yyyy");

            SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dateEnteredForm = dateEnteredFormat.format(dateDelay);
            System.out.println("dateEnteredForm: " + dateEnteredForm);

            String dateDisplay = formatter1.format(dateDelay);
            String currentDateDisplay = formatter2.format(dateDelay);

            List<Integer> printQuantity = newOrderService.countPrintQuantity(dateEnteredForm);
            List<Integer> printedQuantity = newOrderService.countPrintedQuantity(dateEnteredForm);
            List<Integer> packagedQuantity = newOrderService.countPackagedQuantity(dateEnteredForm);
            List<Integer> errorQuantityIn = newOrderService.countErrorQuantityIn(dateEnteredForm);
            List<Integer> totalOrder = newOrderService.countOrder(dateEnteredForm);

            List<Integer> totalLabeled = newTrackingService.countLabeled(dateEnteredForm);
            List<Integer> totalUnlabeled = newTrackingService.countUnlabeled(dateEnteredForm);
            List<Integer> totalShipped = newTrackingService.countShipped(dateEnteredForm);
            List<Integer> totalUnshipped = newTrackingService.countUnshipped(dateEnteredForm);

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

            if (totalUnlabeled.size() == 0 || totalUnlabeled.get(0) == null) {
                unlabeled = new BigDecimal(0);
            } else {
                unlabeled = new BigDecimal(String.valueOf(totalUnlabeled.get(0)));
            }

            if (totalShipped.size() == 0 || totalShipped.get(0) == null) {
                shipped = new BigDecimal(0);
            } else {
                shipped = new BigDecimal(String.valueOf(totalShipped.get(0)));
            }

            if (totalUnshipped.size() == 0 || totalUnshipped.get(0) == null) {
                unshipped = new BigDecimal(0);
            } else {
                unshipped = new BigDecimal(String.valueOf(totalUnshipped.get(0)));
            }

            SendMessageGroup(CHAT_ID,
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

    public void SendAuto() {
        try {
            Date currentDate = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss, E dd/MM/yyyy");

            SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dateEnteredForm = dateEnteredFormat.format(new Date(System.currentTimeMillis()));
            System.out.println("dateEnteredForm: " + dateEnteredForm);

            String dateDisplay = formatter1.format(currentDate);
            String currentDateDisplay = formatter2.format(currentDate);

            List<Integer> printQuantity = newOrderService.countPrintQuantity(dateEnteredForm);
            List<Integer> printedQuantity = newOrderService.countPrintedQuantity(dateEnteredForm);
            List<Integer> packagedQuantity = newOrderService.countPackagedQuantity(dateEnteredForm);
            List<Integer> errorQuantityIn = newOrderService.countErrorQuantityIn(dateEnteredForm);
            List<Integer> totalOrder = newOrderService.countOrder(dateEnteredForm);

            List<Integer> totalLabeled = newTrackingService.countLabeled(dateEnteredForm);
            List<Integer> totalUnlabeled = newTrackingService.countUnlabeled(dateEnteredForm);
            List<Integer> totalShipped = newTrackingService.countShipped(dateEnteredForm);
            List<Integer> totalUnshipped = newTrackingService.countUnshipped(dateEnteredForm);

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

            if (totalUnlabeled.size() == 0 || totalUnlabeled.get(0) == null) {
                unlabeled = new BigDecimal(0);
            } else {
                unlabeled = new BigDecimal(String.valueOf(totalUnlabeled.get(0)));
            }

            if (totalShipped.size() == 0 || totalShipped.get(0) == null) {
                shipped = new BigDecimal(0);
            } else {
                shipped = new BigDecimal(String.valueOf(totalShipped.get(0)));
            }

            if (totalUnshipped.size() == 0 || totalUnshipped.get(0) == null) {
                unshipped = new BigDecimal(0);
            } else {
                unshipped = new BigDecimal(String.valueOf(totalUnshipped.get(0)));
            }

            SendMessageGroup(CHAT_ID,
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

    public void SendAuto2() {
        try {
            Date currentDate = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss, E dd/MM/yyyy");

            SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dateEnteredForm = dateEnteredFormat.format(new Date(System.currentTimeMillis()));
            System.out.println("dateEnteredForm: " + dateEnteredForm);

            String dateDisplay = formatter1.format(currentDate);
            String currentDateDisplay = formatter2.format(currentDate);

            List<Integer> printQuantity = newOrderService.countPrintQuantity(dateEnteredForm);
            List<Integer> printedQuantity = newOrderService.countPrintedQuantity(dateEnteredForm);
            List<Integer> packagedQuantity = newOrderService.countPackagedQuantity(dateEnteredForm);
            List<Integer> errorQuantityIn = newOrderService.countErrorQuantityIn(dateEnteredForm);
            List<Integer> totalOrder = newOrderService.countOrder(dateEnteredForm);

            List<Integer> totalLabeled = newTrackingService.countLabeled(dateEnteredForm);
            List<Integer> totalUnlabeled = newTrackingService.countUnlabeled(dateEnteredForm);
            List<Integer> totalShipped = newTrackingService.countShipped(dateEnteredForm);
            List<Integer> totalUnshipped = newTrackingService.countUnshipped(dateEnteredForm);

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

            if (totalUnlabeled.size() == 0 || totalUnlabeled.get(0) == null) {
                unlabeled = new BigDecimal(0);
            } else {
                unlabeled = new BigDecimal(String.valueOf(totalUnlabeled.get(0)));
            }

            if (totalShipped.size() == 0 || totalShipped.get(0) == null) {
                shipped = new BigDecimal(0);
            } else {
                shipped = new BigDecimal(String.valueOf(totalShipped.get(0)));
            }

            if (totalUnshipped.size() == 0 || totalUnshipped.get(0) == null) {
                unshipped = new BigDecimal(0);
            } else {
                unshipped = new BigDecimal(String.valueOf(totalUnshipped.get(0)));
            }

            SendMessageGroup(CHAT_ID,
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

    public void SendAutoADayDelay() {
        try {
            Date currentDate = new Date(System.currentTimeMillis());
            LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate aDayDelay = localDate.minusDays(1);
            Date dateDelay = Date.from(aDayDelay.atStartOfDay(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("E dd/MM/yyyy");

            SimpleDateFormat dateEnteredFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dateEnteredForm = dateEnteredFormat.format(dateDelay);
            System.out.println("dateEnteredForm: " + dateEnteredForm);

            String dateDisplay = formatter1.format(dateDelay);
            String currentDateDisplay = formatter2.format(dateDelay);

            List<Integer> printQuantity = newOrderService.countPrintQuantity(dateEnteredForm);
            List<Integer> printedQuantity = newOrderService.countPrintedQuantity(dateEnteredForm);
            List<Integer> packagedQuantity = newOrderService.countPackagedQuantity(dateEnteredForm);
            List<Integer> errorQuantityIn = newOrderService.countErrorQuantityIn(dateEnteredForm);
            List<Integer> totalOrder = newOrderService.countOrder(dateEnteredForm);

            List<Integer> totalLabeled = newTrackingService.countLabeled(dateEnteredForm);
            List<Integer> totalUnlabeled = newTrackingService.countUnlabeled(dateEnteredForm);
            List<Integer> totalShipped = newTrackingService.countShipped(dateEnteredForm);
            List<Integer> totalUnshipped = newTrackingService.countUnshipped(dateEnteredForm);

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

            if (totalUnlabeled.size() == 0 || totalUnlabeled.get(0) == null) {
                unlabeled = new BigDecimal(0);
            } else {
                unlabeled = new BigDecimal(String.valueOf(totalUnlabeled.get(0)));
            }

            if (totalShipped.size() == 0 || totalShipped.get(0) == null) {
                shipped = new BigDecimal(0);
            } else {
                shipped = new BigDecimal(String.valueOf(totalShipped.get(0)));
            }

            if (totalUnshipped.size() == 0 || totalUnshipped.get(0) == null) {
                unshipped = new BigDecimal(0);
            } else {
                unshipped = new BigDecimal(String.valueOf(totalUnshipped.get(0)));
            }

            SendMessageGroup(CHAT_ID2,
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
