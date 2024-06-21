package pthk.srthk.wikimedia_producer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pthk.srthk.wikimedia_producer.service.DatabaseService;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger LOG = LoggerFactory.getLogger(WikimediaProducer.class);
    @Autowired
    private DatabaseService databaseService;
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Value("${spring.kafka.website.name}")
    private String url;

    public WikimediaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void startEventSourceAndSendMessage() {
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topicName, databaseService);
        EventSource eventSource = new EventSource.Builder(eventHandler, URI.create(url)).build();
        eventSource.start();

        try {
            TimeUnit.MINUTES.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupted status
            LOG.error("Thread interrupted: {}", e.getMessage());
        }
    }

}
