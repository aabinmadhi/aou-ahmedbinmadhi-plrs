package aou.ahmedbinmadhi.plrs.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfitMapperTest {

    private ProfitMapper profitMapper;

    @BeforeEach
    public void setUp() {
        profitMapper = new ProfitMapperImpl();
    }
}
