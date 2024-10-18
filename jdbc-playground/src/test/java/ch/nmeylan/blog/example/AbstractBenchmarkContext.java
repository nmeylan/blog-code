package ch.nmeylan.blog.example;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

import java.util.Properties;

public class AbstractBenchmarkContext {
    private static PostgreSQLContainer<?> postgreSQLContainer;
    protected static ConfigurableApplicationContext appContext;
    static {
        MountableFile postgresConf = MountableFile.forClasspathResource("postgres/postgres.conf");
        MountableFile postgresSh = MountableFile.forClasspathResource("postgres/postgres.sh");
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.4")
                .withDatabaseName("benchmark_db")
                .withUsername("benchmark")
                .withPassword("benchmark")
                .withCopyToContainer(postgresSh, "/docker-entrypoint-initdb.d/postgres.sh")
                .withCopyToContainer(postgresConf, "/postgres.conf")
                .withCommand("postgres");
        postgreSQLContainer.withReuse(true).start();
    }

    protected static void setup(Class<?> applicationClass) {
        SpringApplication application = new SpringApplication(applicationClass/*, any extra configuration class to import */);
        Properties properties = new Properties();
        properties.put("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        properties.put("spring.datasource.username", postgreSQLContainer.getUsername());
        properties.put("spring.datasource.password", postgreSQLContainer.getPassword());
        application.setAdditionalProfiles("benchmark");
        application.setDefaultProperties(properties);
        appContext = application.run();
    }
}
