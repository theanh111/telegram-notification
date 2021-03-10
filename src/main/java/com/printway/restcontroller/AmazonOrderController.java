package com.printway.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.client.amazongateway.reponse.orders.GetOrdersResponse;
import com.printway.client.amazongateway.reponse.orders.InputGetOrders;
import com.printway.entity.db.Order;
import com.printway.service.AmazonOrderService;
import com.printway.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/amazon/v1")
public class AmazonOrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AmazonOrderService amazonOrderService;

    @RequestMapping(value = "/get-orders", method = RequestMethod.POST)
    public ResponseEntity<?> getOrders(@RequestBody InputGetOrders inputGetOrders) throws JsonProcessingException {
        success(amazonOrderService.getOrders(inputGetOrders));
        return ResponseEntity.status(status).body(body);
    }
}
