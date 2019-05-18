package net.playground.orderservice.rest.io;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playground.orderservice.rest.model.Order;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderRequest {
    private Order order;
}
