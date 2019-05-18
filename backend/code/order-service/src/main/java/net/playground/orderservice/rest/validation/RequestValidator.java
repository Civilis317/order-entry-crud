package net.playground.orderservice.rest.validation;

import net.playground.orderservice.rest.io.OrderRequest;
import net.playground.orderservice.rest.io.QueryParam;

import java.util.Map;

public class RequestValidator {

    public static void validate(Long request) {
        if (request == null) throw new IllegalArgumentException("request may not be empty");
    }

    public static void validate(OrderRequest request) {
        if (request == null) throw new IllegalArgumentException("request may not be empty");
        if (request.getOrder() == null) throw new IllegalArgumentException("request must contain a valid order");
    }

    public static void validate(Map<String, String> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) return;
        paramMap.keySet().forEach(k -> {
            try {
                QueryParam.fromValue(k);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("'" + k + "' is not a valid argument");
            }
        });
    }
}
