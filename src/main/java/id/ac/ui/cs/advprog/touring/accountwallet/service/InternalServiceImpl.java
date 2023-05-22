package id.ac.ui.cs.advprog.touring.accountwallet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.internal.UsersResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternalServiceImpl implements InternalService {
    private final UserRepository userRepository;

    @Override
    public UsersResponse fetchUserIds(Integer[] userIds) {
        List<CompletableFuture<Optional<User>>> futures = new ArrayList<>();
        for (Integer uid : userIds) {
            futures.add(CompletableFuture.supplyAsync(() -> userRepository.findById(uid)));
        }

        var allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        CompletableFuture<List<Optional<User>>> allCompletableFuture = allFutures.thenApply(future -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));

        var completableFuture = allCompletableFuture.thenApply(users -> users.stream()
                .filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList()));

        try {
            return new UsersResponse(completableFuture.get());
        } catch (InterruptedException e) {
            // We don't care it fails...for now
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            // We don't care it fails...for now
        }
        return new UsersResponse(new ArrayList<>());
    }

}
