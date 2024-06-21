package pthk.srthk.wikimedia_producer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import pthk.srthk.wikimedia_producer.entity.RecentChange;
import pthk.srthk.wikimedia_producer.service.DatabaseService;

import java.io.IOException;


@AllArgsConstructor
public class WikimediaChangesHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WikimediaChangesHandler.class);

    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;
    private DatabaseService databaseService;

    @Override
    public void onOpen() throws Exception {
        LOG.info("Connection opened.");
    }

    @Override
    public void onClosed() throws Exception {
        LOG.info("Connection closed.");

    }

    @Override
    public void onComment(String arg0) throws Exception {
        LOG.info("Received comment: {}", arg0);
    }

    @Override
    public void onError(Throwable arg0) {
        LOG.error("An error occurred: {}", arg0.getMessage());
    }


    @Override
    public void onMessage(String arg0, MessageEvent arg1) throws Exception {
        kafkaTemplate.send(topic, arg1.getData());
        databaseService.saveRecentChange(jsonToRecentChange(arg1.getData()));
    }

    public RecentChange jsonToRecentChange(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, RecentChange.class);
    }
}
