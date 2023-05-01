package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.model.Session;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionRepositoryTest {

    @Mock
    private SessionRepository sessionRepository;

    private Session session;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testUser");

        session = new Session();
        session.setId(1);
        session.setUser(user);
        session.setToken("sampleToken");
    }

    @Test
    void testFindByToken() {
        when(sessionRepository.findByToken(any(String.class))).thenReturn(Optional.of(session));

        Optional<Session> foundSession = sessionRepository.findByToken("sampleToken");

        assertThat(foundSession).isPresent();
        assertThat(foundSession.get().getToken()).isEqualTo(session.getToken());
    }

    @Test
    void testFindByUser() {
        when(sessionRepository.findByUser(any(User.class))).thenReturn(Optional.of(session));

        Optional<Session> foundSession = sessionRepository.findByUser(user);

        assertThat(foundSession).isPresent();
        assertThat(foundSession.get().getUser()).isEqualTo(session.getUser());
    }

    @Test
    void testDeleteByToken() {
        doNothing().when(sessionRepository).deleteByToken(any(String.class));

        sessionRepository.deleteByToken(session.getToken());

        verify(sessionRepository, times(1)).deleteByToken(session.getToken());
    }
}
