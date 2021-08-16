package by.valvik.bannermanagement;

import by.valvik.bannermanagement.initializer.Postgres;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

@Sql("/sql/test.sql")
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = {Postgres.Initializer.class})
@Transactional
public abstract class BaseTest {

    @BeforeAll
    public static void createTestContainer() {

        Postgres.container.start();

    }

}
