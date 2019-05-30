package net.playground.orderservice.rest.controller;

import net.playground.orderservice.jpa.service.StorageService;
import net.playground.orderservice.rest.io.OrderRequest;
import net.playground.orderservice.rest.io.OrderResponse;
import net.playground.orderservice.rest.validation.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/secured/api/order")
public class OrderController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final StorageService storageService;

    public OrderController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = {"/get", "/list"})
    public @ResponseBody
    OrderResponse getOrderList(@RequestParam Map<String, String> queryParams) {
        RequestValidator.validate(queryParams);
        PageRequest pageRequest = getPageRequest(queryParams);
        OrderResponse response = new OrderResponse();
        response.assimilatePage(storageService.find(pageRequest));
        return response;
    }

    @GetMapping(value = "/get/{id}")
    public @ResponseBody
    OrderResponse getOrder(@PathVariable(name = "id") Long id) {
        RequestValidator.validate(id);
        OrderResponse response = new OrderResponse();
        response.setSingleItem(storageService.getOrder(id));
        return response;
    }

    @RequestMapping(value = {"/save", "/update"}, method = {POST, PUT})
    public @ResponseBody
    OrderResponse saveOrder(@RequestBody OrderRequest request) {
        RequestValidator.validate(request);
        request.getOrder().setDate(new Date());
        OrderResponse response = new OrderResponse();
        response.setSingleItem(storageService.save(request.getOrder()));
        return response;
    }

    @DeleteMapping(value = "/delete/{id}")
    public @ResponseBody
    Long deleteOrder(@PathVariable(name = "id") Long id) {
        return storageService.delete(id);
    }
}
