package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.model.Session;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findByToken(String token);
    Optional<Session> findByUser(User user);
}
