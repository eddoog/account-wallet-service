package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.model.TopUpApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopUpApprovalRepository extends JpaRepository<TopUpApproval, Integer> {
    @Override
    void deleteById(Integer integer);

    @Override
    List<TopUpApproval> findAll();
}
