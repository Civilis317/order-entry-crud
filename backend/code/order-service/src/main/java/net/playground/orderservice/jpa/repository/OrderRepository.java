package net.playground.orderservice.jpa.repository;

import net.playground.orderservice.jpa.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {
}
