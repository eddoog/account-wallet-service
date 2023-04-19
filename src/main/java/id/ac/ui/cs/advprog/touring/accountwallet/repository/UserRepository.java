package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);

    Optional<User> findByUsername(String username);
}
