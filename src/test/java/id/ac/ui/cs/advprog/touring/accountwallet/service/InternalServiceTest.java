package id.ac.ui.cs.advprog.touring.accountwallet.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InternalServiceTest {
    @InjectMocks
    private InternalServiceImpl service;

    @Mock
    private UserRepository mockUserRepository;

    private User user1, user2;

    @BeforeEach
    void setUp() {
        LocalDateTime createdAt = LocalDateTime.now().minusMinutes(10);

        user1 = User.builder()
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password("password")
                .role(UserType.CUSTOMER)
                .verificationCode(null)
                .isEnabled(true)
                .createdAt(createdAt)
                .build();

        user2 = User.builder()
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password("password")
                .role(UserType.TOURGUIDE)
                .verificationCode("0123456789")
                .isEnabled(false)
                .createdAt(createdAt)
                .build();
    }

    @Test
    void whenUserExistsShouldReturnUsers() {
        when(mockUserRepository.findById(any(Integer.class))).thenAnswer(invocation -> Optional.of(user1));
        var users = service.fetchUserIds(new Integer[] { 1 });
        Assertions.assertEquals(users.getUsers().get(0), user1);

        when(mockUserRepository.findById(any(Integer.class))).thenAnswer(invocation -> Optional.of(user2));
        users = service.fetchUserIds(new Integer[] { 2 });
        Assertions.assertEquals(users.getUsers().get(0), user2);
    }

    @Test
    void whenUserDoesNotExistShouldReturnJustExisting() {
        when(mockUserRepository.findById(any(Integer.class))).thenAnswer(invocation -> Optional.ofNullable(null));
        var users = service.fetchUserIds(new Integer[] { 9 });
        Assertions.assertEquals(0, users.getUsers().size());

        when(mockUserRepository.findById((1))).thenAnswer(invocation -> Optional.of(user1));
        when(mockUserRepository.findById((3))).thenAnswer(invocation -> Optional.ofNullable(null));
        users = service.fetchUserIds(new Integer[] { 1, 3 });
        Assertions.assertEquals(1, users.getUsers().size());
    }
}
