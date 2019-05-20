package net.playground.orderservice.jpa.conversion;

import net.playground.orderservice.jpa.entity.DetailEntity;
import net.playground.orderservice.jpa.entity.OrderEntity;
import net.playground.orderservice.rest.model.Order;
import net.playground.orderservice.rest.model.OrderDetail;

public class OrderConverter {

    public static String getSortField(String fieldname) {
        String result = null;
        switch (fieldname) {
            case "date":
                result = "orderDate";
                break;
            case "employee":
                result = "employee";
                break;
            case "customer":
                result = "employee";
                break;
            case "description":
            default:
                result = "name";
                break;
        }
        return result;
    }

    public static OrderEntity convertOrderToEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setName(order.getDescription());
        entity.setOrderDate(order.getDate());
        entity.setEmployee(order.getEmployee());
        entity.setCustomer(order.getCustomer());
//        order.getOrderDetails().forEach(d -> entity.add(convertDetailToEntity(d)));
        return entity;
    }

    public static Order convertEntityToOrder(OrderEntity entity) {
        Order order = new Order();
        order.setId(entity.getId());
        order.setDescription(entity.getName());
        order.setDate(entity.getOrderDate());
        order.setEmployee(entity.getEmployee());
        order.setCustomer(entity.getCustomer());
//        entity.getDetailList().forEach(de -> order.getOrderDetails().add(convertEntityToDetail(de)));
        return order;
    }

    public static OrderDetail convertEntityToDetail(DetailEntity entity) {
        OrderDetail detail = new OrderDetail();
        detail.setId(entity.getId());
        detail.setProductCode(entity.getCode());
        detail.setDescription(entity.getDescription());
        detail.setPrice(entity.getPrice());
        detail.setAmount(entity.getAmount());
        return detail;
    }

    public static DetailEntity convertDetailToEntity(OrderDetail detail) {
        DetailEntity entity = new DetailEntity();
        entity.setId(detail.getId());
        entity.setCode(detail.getProductCode());
        entity.setDescription(detail.getDescription());
        entity.setPrice(detail.getPrice());
        entity.setAmount(detail.getAmount());
        return entity;
    }
}
