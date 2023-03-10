package aou.ahmedbinmadhi.plrs.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import aou.ahmedbinmadhi.plrs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlrsCommissionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlrsCommissionDTO.class);
        PlrsCommissionDTO plrsCommissionDTO1 = new PlrsCommissionDTO();
        plrsCommissionDTO1.setId(1L);
        PlrsCommissionDTO plrsCommissionDTO2 = new PlrsCommissionDTO();
        assertThat(plrsCommissionDTO1).isNotEqualTo(plrsCommissionDTO2);
        plrsCommissionDTO2.setId(plrsCommissionDTO1.getId());
        assertThat(plrsCommissionDTO1).isEqualTo(plrsCommissionDTO2);
        plrsCommissionDTO2.setId(2L);
        assertThat(plrsCommissionDTO1).isNotEqualTo(plrsCommissionDTO2);
        plrsCommissionDTO1.setId(null);
        assertThat(plrsCommissionDTO1).isNotEqualTo(plrsCommissionDTO2);
    }
}
