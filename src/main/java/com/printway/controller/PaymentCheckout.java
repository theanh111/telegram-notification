package com.printway.controller;

import com.printway.entity.db.Transaction;
import com.printway.service.PaymentService;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@Controller
public class PaymentCheckout {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/callback-paypal/{transactionId}", method = RequestMethod.GET)
    public ModelAndView getCaptureOrder(@PathVariable String transactionId) throws SQLException {
        ModelAndView modelAndView = null;
        Transaction transaction = paymentService.captureOrderPaypal(transactionId);
        if (transaction.getStatus().equals("Completed")) {
            modelAndView = new ModelAndView("Success");
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("Error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/callback-stripe/{paymentIntentId}/{transactionId}", method = RequestMethod.GET)
    public ModelAndView completedPaymentStripe(@PathVariable String paymentIntentId, @PathVariable String transactionId) throws SQLException {
        ModelAndView modelAndView = null;
        PaymentIntent paymentIntent = paymentService.completedPaymentStripe(paymentIntentId, transactionId);
        if (paymentIntent.getStatus().equals("succeeded")) {
            modelAndView = new ModelAndView("Success");
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("Error");
            return modelAndView;
        }
    }

}
