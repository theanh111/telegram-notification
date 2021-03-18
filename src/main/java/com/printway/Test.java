//package com.printway;
//
//import com.printway.service.NewOrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.text.ParseException;
//import java.util.Date;
//
//@Component
//public class Test {
//
//    @Autowired
//    private NewOrderService newOrderService;
//
//    @PostConstruct
//    public void Test() throws ParseException {
//        Integer a = new Integer(747);
//        System.out.println(newOrderService.findById(a));
//        System.out.println(newOrderService.countPrintQuantity(new Date(System.currentTimeMillis())));
//    }
//}
