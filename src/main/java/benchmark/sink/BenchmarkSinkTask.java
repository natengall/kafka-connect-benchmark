package benchmark.sink;

import org.HdrHistogram.Histogram;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class BenchmarkSinkTask extends SinkTask {
  private static final Logger log = LoggerFactory.getLogger(BenchmarkSinkTask.class);

  BenchmarkSinkConfig config;
  String connectorName;
  Histogram histogram = new Histogram(3600000000000L, 3);

  @Override
  public void start(final Map<String, String> props) {
    log.info("Starting task");
    config = new BenchmarkSinkConfig(props);
    connectorName = config.originalsStrings().get("name");
    new Thread(new Runnable(){
        public void run(){
        	while (true) {
        		try {
        			Thread.sleep(5000);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
        		histogram.outputPercentileDistribution(System.out, 1000.0);
        	}
      }
    }).start();
  }

  @Override
  public void put(Collection<SinkRecord> records) {
    if (records.isEmpty()) {
      return;
    }    
    for (SinkRecord record : records) {
      Struct value = (Struct) record.value();
      histogram.recordValue(System.currentTimeMillis() - ((Date)(value.get("modstamp"))).getTime());
    }
  }

  @Override
  public void flush(Map<TopicPartition, OffsetAndMetadata> map) {
    // Not necessary
  }

  public void stop() {
    log.info("Stopping task");
  }

  @Override
  public String version() {
    return getClass().getPackage().getImplementationVersion();
  }

}
