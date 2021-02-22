package com.printway.restcontroller;

import com.printway.client.paypalgateway.input.AuthSeller;
import com.printway.client.paypalgateway.response.GetCredentialResponse;
import com.printway.client.paypalgateway.response.OrderCreateResponse;
import com.printway.entity.db.Order;
import com.printway.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/paypal/v1")
public class PaypalController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/{userId}/create", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody List<Order> orderList, @PathVariable String userId) {
        OrderCreateResponse order = null;
        order = paymentService.createOrderPaypal(orderList, userId);
        return new ResponseEntity<OrderCreateResponse>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}/get-link-signup", method = RequestMethod.POST)
    public ResponseEntity<?> getLinkSignUp(@PathVariable String userId) {
        String response = paymentService.getLinkOnboardPaypal(userId);
        HashMap<String, String> data = new HashMap<>();
        data.put("href",response);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}/get-authentication", method = RequestMethod.POST)
    @CrossOrigin("*")
    public ResponseEntity<?> getAuthentication(@PathVariable String userId, @RequestBody AuthSeller authSeller) {
        try {
            GetCredentialResponse response = paymentService.getCredentialPaypal(authSeller, userId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw e;
        }
    }
}
