package id.ac.ui.cs.advprog.touring.accountwallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_otp")
public class OneTimePassword {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;
    @Column(nullable = false)
    private Integer oneTimePasswordCode;
    @Column(nullable = false)
    private LocalDateTime createdAt;
}