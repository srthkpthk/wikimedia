package pthk.srthk.wikimedia_producer;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Wikimedia producer.
 */
@SpringBootApplication
@AllArgsConstructor
public class WikimediaProducerApplication implements CommandLineRunner {

    private WikimediaProducer wikimediaProducer;

    public static void main(String[] args) {
        SpringApplication.run(WikimediaProducerApplication.class, args);
    }

    /**
     * Runs the Wikimedia producer after the application has started.
     * @param args Command line arguments.
     * @throws Exception if an error occurs while sending the message.
     */
    public void run(String... args) throws Exception {
        wikimediaProducer.startEventSourceAndSendMessage();
    }

}