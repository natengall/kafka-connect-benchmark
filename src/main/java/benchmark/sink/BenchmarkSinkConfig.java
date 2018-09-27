package benchmark.sink;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BenchmarkSinkConfig extends AbstractConfig {

  public enum InsertMode {
    INSERT,
    UPSERT;
  }

  public enum PrimaryKeyMode {
    NONE,
    KAFKA,
    RECORD_KEY,
    RECORD_VALUE;
  }

  public static final List<String> DEFAULT_KAFKA_PK_NAMES = Arrays.asList(
      "__connect_topic",
      "__connect_partition",
      "__connect_offset"
  );

  public static final String TRACK = "track";
  private static final String TRACK_DEFAULT = "";
  private static final String TRACK_DOC =
      "List of comma-separated record values to track into Graphite. If empty, no fields from the record value are utilized.";
  private static final String TRACK_DISPLAY = "Track";

  private static final String BENCHMARK_GROUP = "Benchmark";

  public static final ConfigDef CONFIG_DEF = new ConfigDef()
      .define(TRACK, ConfigDef.Type.LIST, TRACK_DEFAULT,
              ConfigDef.Importance.MEDIUM, TRACK_DOC,
              BENCHMARK_GROUP, 4, ConfigDef.Width.LONG, TRACK_DISPLAY);

  public BenchmarkSinkConfig(Map<?, ?> props) {
    super(CONFIG_DEF, props);
  }

  public static void main(String... args) {
    System.out.println(CONFIG_DEF.toRst());
  }

}