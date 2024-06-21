package pthk.srthk.wikimedia_producer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "meta")
@Data
public class Meta {
    private String dt;
    private int partition;
    @Column(name = "offsets")
    private long offset;
    private String stream;
    private String domain;
    private String topic;
    @Id
    private String id;
    private String uri;
    private String requestId;

    public void truncateFields() {
        dt = truncate(dt);
        stream = truncate(stream);
        domain = truncate(domain);
        topic = truncate(topic);
        id = truncate(id);
        uri = truncate(uri);
        requestId = truncate(requestId);
    }

    private String truncate(String field) {
        if (field != null && field.length() > 255) {
            return field.substring(0, 255);
        }
        return field;
    }
}