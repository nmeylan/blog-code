package ch.nmeylan.blog;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceIT extends IntegrationTestClass {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserServiceNoAtTransactionalImpl noAtTransactionalUserService;
    @Autowired
    HikariDataSource dataSource;

    @Test
    public void nameValidation() throws InterruptedException {
        // Given
        // When
        ExecutorService executorService = Executors.newFixedThreadPool(12);
        for(int i = 0; i < 12; i++) {
           CompletableFuture.runAsync(() -> userService.nameValidation("John doe"), executorService);
        }
        Thread.sleep(100);
        // Then
        // max pool size is 10
        assertThat(dataSource.getHikariPoolMXBean().getActiveConnections()).isEqualTo(10);
        assertThat(dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection()).isEqualTo(2);
    }

    @Test
    public void insertNoAtTransactional() {
        // Given
        // When
        noAtTransactionalUserService.insert(new UserEntity().setId(UUID.randomUUID().toString()));
        // Then
    }
}
