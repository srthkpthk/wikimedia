package pthk.srthk.wikimedia_producer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pthk.srthk.wikimedia_producer.entity.RecentChange;
import pthk.srthk.wikimedia_producer.repo.DatabaseRepo;
import pthk.srthk.wikimedia_producer.repo.MetaRepo;
import pthk.srthk.wikimedia_producer.repo.RevisionRepo;

@Service
@AllArgsConstructor
public class DatabaseService {

    private DatabaseRepo databaseRepo;
    private MetaRepo metaRepo;
    private RevisionRepo revisionRepo;

    public void saveRecentChange(RecentChange recentChange) {


        assert recentChange != null;
        if (recentChange.getRevision() != null) {
            revisionRepo.save(recentChange.getRevision());
        }

        if (recentChange.getLength() != null) {

            revisionRepo.save(recentChange.getLength());
        }

        if (recentChange.getMeta() != null) {
            recentChange.getMeta().truncateFields();
            metaRepo.save(recentChange.getMeta());
        }
        recentChange.truncateFields();
        databaseRepo.save(recentChange);

    }
}
