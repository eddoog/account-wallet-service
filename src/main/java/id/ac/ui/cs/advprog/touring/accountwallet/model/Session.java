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
public class Session {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String token;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
