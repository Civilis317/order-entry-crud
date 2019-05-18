package net.playground.orderservice.rest.io;

import javassist.NotFoundException;

import java.util.Arrays;
import java.util.List;

public enum QueryParam {
    PAGE("page"),
    SORT("sort"),
    PRODUCTCODE("product"),
    EMPLOYEE("employee"),
    CUSTOMER("customer");

    private final String text;

    QueryParam(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static QueryParam fromValue(String value) {
        List<QueryParam> list = Arrays.asList(QueryParam.values());
        return list.stream().filter(m -> m.text.equals(value)).findAny().orElseThrow(IllegalArgumentException::new);
    }
}
