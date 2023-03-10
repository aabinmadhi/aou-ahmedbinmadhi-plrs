package aou.ahmedbinmadhi.plrs.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationServiceMapperTest {

    private LocationServiceMapper locationServiceMapper;

    @BeforeEach
    public void setUp() {
        locationServiceMapper = new LocationServiceMapperImpl();
    }
}
