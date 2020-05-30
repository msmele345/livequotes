package com.mitchmele.livequotes.sqlserver;

import com.mitchmele.livequotes.models.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AskRepository extends JpaRepository<Ask, Integer> {
}
