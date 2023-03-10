package aou.ahmedbinmadhi.plrs.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyticsCenterMapperTest {

    private AnalyticsCenterMapper analyticsCenterMapper;

    @BeforeEach
    public void setUp() {
        analyticsCenterMapper = new AnalyticsCenterMapperImpl();
    }
}
