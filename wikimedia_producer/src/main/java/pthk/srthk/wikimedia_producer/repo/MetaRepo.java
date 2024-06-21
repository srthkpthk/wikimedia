package pthk.srthk.wikimedia_producer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pthk.srthk.wikimedia_producer.entity.Meta;

public interface MetaRepo extends JpaRepository<Meta, Long> {
}
