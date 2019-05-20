package net.playground.orderservice.rest.io;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playground.orderservice.rest.model.Order;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
//@JsonPropertyOrder({"count", "orders"})
public class OrderResponse {
    private Integer count;
    private Integer page;
    private Long totalCount;
    private Integer totalPages;
    private Integer pageSize;
    private List<Order> data;

    // convenience methods
    public void setSingleItem(Order tm) {
        initData();
        this.data.add(tm);
        this.count = data.size();
    }

    public void assimilatePage(Page<Order> page) {
        initData();
        if (page.hasContent()) {
            this.data.addAll(page.getContent());
            this.count = page.getNumberOfElements();
            this.page = page.getNumber() + 1;
            this.totalCount = page.getTotalElements();
            this.totalPages = page.getTotalPages();
            this.pageSize = page.getSize();

        } else {
            this.count = 0;
            this.page = 1;
            this.totalCount = Long.valueOf("0");
            this.totalPages = 1;
            this.pageSize = page.getSize();
        }
    }

    private void initData() {
        if (data == null) {
            data = new ArrayList<>(1024);
        }
        data.clear();
    }
}
