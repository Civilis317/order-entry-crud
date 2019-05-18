package net.playground.orderservice.jpa.repository;

import net.playground.orderservice.jpa.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
