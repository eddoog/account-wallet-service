package id.ac.ui.cs.advprog.touring.accountwallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @GeneratedValue
    @Column()
    private String userName;

    // Personal Data
    @Column(nullable = false)
    private String name;
    @Column()
    private String phoneNum;
    @Column()
    private String birthDate;
    @Column()
    private String gender;
    @Column()
    private String domicile;
}
