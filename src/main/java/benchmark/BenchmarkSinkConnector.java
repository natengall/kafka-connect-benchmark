package benchmark;

import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import benchmark.sink.BenchmarkSinkConfig;
import benchmark.sink.BenchmarkSinkTask;
import benchmark.util.Version;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class BenchmarkSinkConnector extends SinkConnector {
  private static final Logger log = LoggerFactory.getLogger(BenchmarkSinkConnector.class);

  private Map<String, String> configProps;

  public Class<? extends Task> taskClass() {
    return BenchmarkSinkTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    log.info("Setting task configurations for {} workers.", maxTasks);
    final List<Map<String, String>> configs = new ArrayList<Map<String, String>>(maxTasks);
    for (int i = 0; i < maxTasks; ++i) {
      configs.add(configProps);
    }
    return configs;
  }

  @Override
  public void start(Map<String, String> props) {
    configProps = props;
  }

  @Override
  public void stop() {
  }

  @Override
  public ConfigDef config() {
    return BenchmarkSinkConfig.CONFIG_DEF;
  }

  @Override
  public Config validate(Map<String, String> connectorConfigs) {
    // TODO cross-fields validation here: pkFields against the pkMode
    return super.validate(connectorConfigs);
  }

  @Override
  public String version() {
    return Version.getVersion();
  }
}
