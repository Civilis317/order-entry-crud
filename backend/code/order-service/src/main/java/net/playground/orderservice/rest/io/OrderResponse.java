package net.playground.orderservice.rest.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playground.orderservice.rest.model.Order;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonPropertyOrder({"count", "orders"})
public class OrderResponse {
    @JsonProperty("orders")
    private List<Order> orderList = new ArrayList<>(1024);

    public int getCount() {
        if (orderList == null) return 0;
        return orderList.size();
    }
}
