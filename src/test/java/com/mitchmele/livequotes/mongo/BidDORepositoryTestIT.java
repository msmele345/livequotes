package com.mitchmele.livequotes.mongo;

import com.mitchmele.livequotes.models.BidDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BidDORepositoryTestIT {

    @Autowired
    private BidDORepository repository;

    @BeforeEach
    void setUp() {
        BidDO bid1 = BidDO.builder().id(1).symbol("ABC").bidPrice(BigDecimal.valueOf(3.0)).build();
        BidDO bid2 = BidDO.builder().id(2).symbol("ABC").bidPrice(BigDecimal.valueOf(3.50)).build();

        repository.saveAll(asList(bid1, bid2));
    }

    @Test
    void save_persistsTheBidCorrectly() {

        List<BidDO> actual = repository.findAll();
        assertThat(actual).hasSize(2);
    }
}