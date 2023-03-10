package aou.ahmedbinmadhi.plrs.domain;

import static org.assertj.core.api.Assertions.assertThat;

import aou.ahmedbinmadhi.plrs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationService.class);
        LocationService locationService1 = new LocationService();
        locationService1.setId(1L);
        LocationService locationService2 = new LocationService();
        locationService2.setId(locationService1.getId());
        assertThat(locationService1).isEqualTo(locationService2);
        locationService2.setId(2L);
        assertThat(locationService1).isNotEqualTo(locationService2);
        locationService1.setId(null);
        assertThat(locationService1).isNotEqualTo(locationService2);
    }
}
