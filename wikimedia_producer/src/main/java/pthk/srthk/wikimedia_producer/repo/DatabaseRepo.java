package pthk.srthk.wikimedia_producer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pthk.srthk.wikimedia_producer.entity.RecentChange;

public interface DatabaseRepo extends JpaRepository<RecentChange, Long> {
}
