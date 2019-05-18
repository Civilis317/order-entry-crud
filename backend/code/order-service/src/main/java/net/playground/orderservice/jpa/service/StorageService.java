package net.playground.orderservice.jpa.service;

import net.playground.orderservice.jpa.conversion.OrderConverter;
import net.playground.orderservice.jpa.entity.OrderEntity;
import net.playground.orderservice.jpa.repository.OrderRepository;
import net.playground.orderservice.rest.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {
    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    private final OrderRepository repository;

    public StorageService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>(1024);
        repository.findAll().forEach(oe -> orderList.add(OrderConverter.convertEntityToOrder(oe)));
        return orderList;
    }

    public Order getOrder(Long id) {
        OrderEntity entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Record not found for id: " + id));
        return OrderConverter.convertEntityToOrder(entity);
    }

    public Order save(Order order) {
        OrderEntity savedEntity = repository.save(OrderConverter.convertOrderToEntity(order));
        return OrderConverter.convertEntityToOrder(savedEntity);
    }
}
