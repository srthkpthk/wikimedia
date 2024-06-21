package pthk.srthk.wikimedia_producer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "revision")
@Data
public class Revision {
    private long jsonMemberNew;
    private long old;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

}