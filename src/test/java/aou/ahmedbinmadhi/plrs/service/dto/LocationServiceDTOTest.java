package aou.ahmedbinmadhi.plrs.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import aou.ahmedbinmadhi.plrs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationServiceDTO.class);
        LocationServiceDTO locationServiceDTO1 = new LocationServiceDTO();
        locationServiceDTO1.setId(1L);
        LocationServiceDTO locationServiceDTO2 = new LocationServiceDTO();
        assertThat(locationServiceDTO1).isNotEqualTo(locationServiceDTO2);
        locationServiceDTO2.setId(locationServiceDTO1.getId());
        assertThat(locationServiceDTO1).isEqualTo(locationServiceDTO2);
        locationServiceDTO2.setId(2L);
        assertThat(locationServiceDTO1).isNotEqualTo(locationServiceDTO2);
        locationServiceDTO1.setId(null);
        assertThat(locationServiceDTO1).isNotEqualTo(locationServiceDTO2);
    }
}
