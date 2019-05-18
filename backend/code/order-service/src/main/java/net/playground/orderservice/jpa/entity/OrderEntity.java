package net.playground.orderservice.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table (name = "demo_order")
public class OrderEntity {

    @Id
    @SequenceGenerator(name="id_seq", sequenceName = "odr_seq", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="order_date", nullable = false)
    private Date orderDate;

    @Column(name="employee")
    private String employee;

    @Column(name="customer")
    private String customer;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<DetailEntity> detailList = new ArrayList<>(128);

    public void add(DetailEntity detailEntity) {
        if(detailEntity != null) {
            this.detailList.add(detailEntity);
            detailEntity.setOrderEntity(this);
        }
    }
}
