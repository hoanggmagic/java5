package asm.asm.Model;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Account account;  // Liên kết với Account qua username

    @Column(name = "order_date")
    private String orderDate;

    // Các trường khác nếu có
}
