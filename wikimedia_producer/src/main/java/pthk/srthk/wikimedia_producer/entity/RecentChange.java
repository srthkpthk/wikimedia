package pthk.srthk.wikimedia_producer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "recent_changes",schema = "public")
public class RecentChange {
    private String serverScriptPath;
    private String serverName;
    private boolean minor;
    private boolean bot;
    private String wiki;
    @ManyToOne
    @JoinColumn(name = "length_id")
    private Revision length;
    private String type;
    private String title;
    private String notifyUrl;
    @ManyToOne
    @JoinColumn(name = "revision_id")
    private Revision revision;
    private String titleUrl;
    private boolean patrolled;
    @ManyToOne
    @JoinColumn(name = "meta_id")
    private Meta meta;
    private int namespace;
    private String comment;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String serverUrl;
    @Column(name = "user_name")
    private String user;
    private String parsedcomment;
    private int timestamp;


    public void truncateFields() {
        serverScriptPath = truncate(serverScriptPath);
        serverName = truncate(serverName);
        wiki = truncate(wiki);
        type = truncate(type);
        title = truncate(title);
        notifyUrl = truncate(notifyUrl);
        titleUrl = truncate(titleUrl);
        comment = truncate(comment);
        serverUrl = truncate(serverUrl);
        user = truncate(user);
        parsedcomment = truncate(parsedcomment);
    }

    private String truncate(String field) {
        if (field != null && field.length() > 255) {
            return field.substring(0, 255);
        }
        return field;
    }
}
