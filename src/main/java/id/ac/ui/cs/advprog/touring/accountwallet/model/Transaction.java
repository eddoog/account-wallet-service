package id.ac.ui.cs.advprog.touring.accountwallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne()
    private User user;
    @Column(nullable = false)
    private double transactionAmount;
    @Column()
    private String message;
}