package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.model.TopUpApproval;
import id.ac.ui.cs.advprog.touring.accountwallet.model.Transaction;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TopUpApprovalRepositoryTest {

    @Mock
    private TopUpApprovalRepository topUpApprovalRepository;

    private TopUpApproval topUpApproval;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testUser");

        topUpApproval = new TopUpApproval();
        topUpApproval.setId(1);
        topUpApproval.setUser(user);
        topUpApproval.setTransactionAmount(10);
    }

    @Test
    void testFindAll() {
        when(topUpApprovalRepository.findAll()).thenReturn(List.of(topUpApproval));

        List<TopUpApproval> allTopUpApproval = topUpApprovalRepository.findAll();

        assertNotNull(allTopUpApproval);
        assertEquals(1, allTopUpApproval.size());
    }

    @Test
    void testDeleteById() {
        doNothing().when(topUpApprovalRepository).deleteById(any(Integer.class));

        topUpApprovalRepository.deleteById(topUpApproval.getId());

        verify(topUpApprovalRepository, times(1)).deleteById(topUpApproval.getId());
    }
}