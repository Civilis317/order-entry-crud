package net.playground.orderservice.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "demo_detail")
public class DetailEntity {

    @Id
    @SequenceGenerator(name="id_seq", sequenceName = "dts_seq", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Long id;

    @Column(name="code", nullable = false)
    private String code;

    @Column(name="name")
    private String description;

    @Column(name="piece_price", nullable = false)
    private Double price;

    @Column(name="amount", nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "odr_id")
    private OrderEntity orderEntity;
}
