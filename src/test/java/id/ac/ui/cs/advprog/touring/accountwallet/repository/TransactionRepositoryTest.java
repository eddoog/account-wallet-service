package id.ac.ui.cs.advprog.touring.accountwallet.repository;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    private Transaction transaction;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testUser");

        transaction = new Transaction();
        transaction.setId(1);
        transaction.setUser(user);
        transaction.setTransactionAmount(10);
    }

    @Test
    void testFindAll() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<Transaction> allTransaction = transactionRepository.findAll();

        assertNotNull(allTransaction);
        assertEquals(1, allTransaction.size());
    }
}
