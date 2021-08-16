package by.valvik.bannermanagement.initializer;

import lombok.experimental.UtilityClass;
import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@UtilityClass
public class Postgres {

    final static DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:latest");

    @ClassRule
    public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(POSTGRES_IMAGE);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues.of("spring.datasource.url=" + container.getJdbcUrl(),
                                  "spring.datasource.username=" + container.getUsername(),
                                  "spring.datasource.password=" + container.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());

        }

    }

}
