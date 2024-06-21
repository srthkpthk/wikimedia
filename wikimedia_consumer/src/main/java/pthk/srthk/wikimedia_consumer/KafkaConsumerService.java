package pthk.srthk.wikimedia_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);
    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "yourGroup")
    public void listen(String message) {
        myWebSocketHandler.broadcast(message);
    }
}

