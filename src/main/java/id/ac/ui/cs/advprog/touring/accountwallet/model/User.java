package id.ac.ui.cs.advprog.touring.accountwallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private UserType role;

    @Column
    private String verificationCode;

    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column()
    private String fullName;
    @Column()
    private String phoneNum;
    @Column()
    private String birthDate;
    @Column()
    private String gender;
    @Column()
    private String domicile;

    @Value("0")
    @Column()
    private double walletAmount;
}
