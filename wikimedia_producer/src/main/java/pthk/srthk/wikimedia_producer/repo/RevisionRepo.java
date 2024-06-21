package pthk.srthk.wikimedia_producer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pthk.srthk.wikimedia_producer.entity.Revision;

public interface RevisionRepo extends JpaRepository<Revision, Long> {
}
