package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.model.OneTimePassword;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OneTimePasswordRepositoryTest {
    @Mock
    private OneTimePasswordRepository mockOneTimePasswordRepository;
    @Test
    void testFindByOneTimePasswordCode() {
        Integer oneTimePasswordCode = 123456;
        var expectedOneTimePassword = OneTimePassword.builder().oneTimePasswordCode(oneTimePasswordCode).build();

        when(mockOneTimePasswordRepository.findByOneTimePasswordCode(oneTimePasswordCode))
                .thenReturn(Optional.of(expectedOneTimePassword));

        Optional<OneTimePassword> actualOneTimePassword = mockOneTimePasswordRepository.findByOneTimePasswordCode(oneTimePasswordCode);

        assertEquals(expectedOneTimePassword, actualOneTimePassword.get());
    }

    @Test
    void testDeleteByUser() {
        var user = User.builder().build();

        mockOneTimePasswordRepository.deleteByUser(user);

        verify(mockOneTimePasswordRepository, times(1)).deleteByUser(user);
    }
}
