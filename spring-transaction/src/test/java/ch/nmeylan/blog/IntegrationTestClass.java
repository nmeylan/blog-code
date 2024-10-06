package ch.nmeylan.blog;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class IntegrationTestClass {
     private static PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.4")
                .withDatabaseName("test_db")
                .withUsername("test")
                .withPassword("test")
                .withCommand("postgres");
        postgreSQLContainer.withReuse(true).start();
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl() + "&ApplicationName=nmeylan-blog-example-app");
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    }
}
