package com.github.drednote.telegramstatemachine.quarkusstarter.jpa.config;

import io.quarkus.test.common.DevServicesContext;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

/**
 * Abstract class for integration tests with sql. Reuses single sql test container over all tests.
 * Tests run faster. Easier to implement new tests.
 */
@Slf4j
public class SqlTestContainerResource implements QuarkusTestResourceLifecycleManager,
    DevServicesContext.ContextAware {

  private Optional<String> containerNetworkId;
  private PostgreSQLContainer<?> container;
  static final Integer PORT = 5432;
  static final String DB_NAME = "test";

  @Override
  public void setIntegrationTestContext(DevServicesContext context) {
    containerNetworkId = context.containerNetworkId();
  }

  @Override
  public Map<String, String> start() {
    // start a container making sure to call withNetworkMode() with the value of containerNetworkId if present
    container = new PostgreSQLContainer<>("postgres:latest")
        .withExposedPorts(PORT)
        .withDatabaseName(DB_NAME)
        .withReuse(true)
        .withLogConsumer(outputFrame -> {
        });

    // apply the network to the container
    containerNetworkId.ifPresent(container::withNetworkMode);

    // start container before retrieving its URL or other properties
    container.start();

    String jdbcUrl = container.getJdbcUrl();
    if (containerNetworkId.isPresent()) {
      // Replace hostname + port in the provided JDBC URL with the hostname of the Docker container
      // running PostgreSQL and the listening port.
      jdbcUrl = fixJdbcUrl(jdbcUrl);
    }

    // return a map containing the configuration the application needs to use the service
    return ImmutableMap.of(
        "quarkus.datasource.username", container.getUsername(),
        "quarkus.datasource.password", container.getPassword(),
        "quarkus.datasource.jdbc.url", jdbcUrl);
  }

  private String fixJdbcUrl(String jdbcUrl) {
    // Part of the JDBC URL to replace
    String hostPort =
        container.getHost() + ':' + container.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT);

    // Host/IP on the container network plus the unmapped port
    String networkHostPort =
        container.getCurrentContainerInfo().getConfig().getHostName()
            + ':'
            + PostgreSQLContainer.POSTGRESQL_PORT;

    return jdbcUrl.replace(hostPort, networkHostPort);
  }

  @Override
  public void stop() {
    container.stop();
  }
}
