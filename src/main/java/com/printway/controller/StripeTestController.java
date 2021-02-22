package com.printway.controller;

import com.printway.client.stripegateway.response.PaymentResponse;
import com.printway.entity.db.Order;
import com.printway.entity.db.PaymentConfig;
import com.printway.service.OrderService;
import com.printway.service.PaymentConfigService;
import com.printway.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.SetupIntent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class StripeTestController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentConfigService paymentConfigService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        List<Order> orderList = orderService.getListOrderByStatus("Unpaid");
        ModelAndView modelAndView = new ModelAndView("/stripe/home");
        modelAndView.addObject("orderList", orderList);
        return modelAndView;
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ModelAndView payment() throws StripeException {
//        byte[] decodedBytes = Base64.getDecoder().decode(info);
//        String decodedString = new String(decodedBytes);
//        List<Order> list = Arrays.asList(new GsonBuilder().create().fromJson(decodedString, Order[].class));
//        String clientSecret = stripeService.createPaymentIntent(list).getClientSecret();
//        ModelAndView modelAndView = new ModelAndView("checkout");
//        modelAndView.addObject("clientSecret", clientSecret);
//        return modelAndView;
        return new ModelAndView("/stripe/checkout");
    }

    @RequestMapping(value = "/cancel")
    public ModelAndView cancel() {
        return new ModelAndView("/stripe/cancel");
    }

    @RequestMapping(value = "/success")
    public ModelAndView success() {
        return new ModelAndView("/stripe/success");
    }

    @RequestMapping(value = "/auto")
    public ModelAndView auto(@RequestParam(required = true) String id) throws StripeException {
        PaymentResponse response = paymentService.createSetupIntentStripe(id);
        ModelAndView modelAndView = new ModelAndView("/stripe/automatic");
        modelAndView.addObject("client_secret", response.getClientSecret());
        modelAndView.addObject("payment_config_id", response.getData().get("payment_config_id"));
        return modelAndView;
    }
    @RequestMapping(value = "/auto-success")
    public ModelAndView autoSuccess(@RequestParam(required = true) String paymentConfigId) throws StripeException {
        PaymentConfig paymentConfig = paymentConfigService.getById(Integer.parseInt(paymentConfigId));
        paymentConfig.setPaymentType("STRIPE");
        paymentConfigService.updatePaymentConfig(paymentConfig);
        ModelAndView modelAndView = new ModelAndView("/Success");
        return modelAndView;
    }

    @RequestMapping(value = "/signup-paypal")
    public ModelAndView getLinkSignUp() {
        return new ModelAndView("/SignUp");
    }
}
