package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.model.OneTimePassword;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Integer> {
    Optional<OneTimePassword> findByOneTimePasswordCode(Integer oneTimePasswordCode);
    void deleteByUser(User user);
}