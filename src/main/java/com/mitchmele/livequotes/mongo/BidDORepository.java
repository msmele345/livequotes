package com.mitchmele.livequotes.mongo;

import com.mitchmele.livequotes.models.BidDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidDORepository extends MongoRepository<BidDO, String > {
}
