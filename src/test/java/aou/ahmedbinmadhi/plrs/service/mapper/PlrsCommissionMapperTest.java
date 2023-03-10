package aou.ahmedbinmadhi.plrs.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlrsCommissionMapperTest {

    private PlrsCommissionMapper plrsCommissionMapper;

    @BeforeEach
    public void setUp() {
        plrsCommissionMapper = new PlrsCommissionMapperImpl();
    }
}
