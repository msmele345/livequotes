package com.mitchmele.livequotes.mongo;

import com.mitchmele.livequotes.models.AskDO;
import com.mitchmele.livequotes.models.BidDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AskDORepository extends MongoRepository<AskDO, String > {
}
