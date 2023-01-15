package com.github.drednote.telegramstatemachine.springstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Abstract class for integration tests with sql. Reuses single sql test container over all tests.
 * Tests run faster. Easier to implement new tests.
 */
@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public abstract class SqlTestContainerBase {

  static final PostgreSQLContainer<?> container;
  static final Integer PORT = 5432;
  static final String DB_NAME = "test";

  static {
    DockerImageName imageName = DockerImageName.parse("postgres");
    container = new PostgreSQLContainer<>(imageName)
        .withExposedPorts(PORT)
        .withDatabaseName(DB_NAME)
        .withReuse(true);
    container.start();
  }

  @DynamicPropertySource
  static void dataSourceConfig(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.username", container::getUsername);
    registry.add("spring.datasource.password", container::getPassword);
  }

}
