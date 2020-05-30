package com.mitchmele.livequotes.sqlserver;

import com.mitchmele.livequotes.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
}
