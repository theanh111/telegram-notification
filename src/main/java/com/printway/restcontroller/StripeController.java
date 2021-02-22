package com.printway.restcontroller;

import com.printway.client.stripegateway.response.PaymentResponse;
import com.printway.entity.db.Order;
import com.printway.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/stripe/v1")
public class StripeController {
    @Autowired
    private PaymentService paymentService;

    @CrossOrigin("*")
    @RequestMapping(value = "/{userId}/create", method = RequestMethod.POST)
    public ResponseEntity<?> createPaymentIntent(@RequestBody List<Order> orderList, @PathVariable String userId) {
        PaymentResponse paymentResponse = paymentService.createPaymentIntentStripe(userId, orderList);
        return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}/create-setup-intent", method = RequestMethod.POST)
    public ResponseEntity<?> createPaymentIntent(@PathVariable String userId) {
        PaymentResponse paymentResponse = paymentService.createSetupIntentStripe(userId);
        return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);
    }

}
