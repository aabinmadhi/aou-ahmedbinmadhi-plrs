package aou.ahmedbinmadhi.plrs.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import aou.ahmedbinmadhi.plrs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyticsCenterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyticsCenterDTO.class);
        AnalyticsCenterDTO analyticsCenterDTO1 = new AnalyticsCenterDTO();
        analyticsCenterDTO1.setId(1L);
        AnalyticsCenterDTO analyticsCenterDTO2 = new AnalyticsCenterDTO();
        assertThat(analyticsCenterDTO1).isNotEqualTo(analyticsCenterDTO2);
        analyticsCenterDTO2.setId(analyticsCenterDTO1.getId());
        assertThat(analyticsCenterDTO1).isEqualTo(analyticsCenterDTO2);
        analyticsCenterDTO2.setId(2L);
        assertThat(analyticsCenterDTO1).isNotEqualTo(analyticsCenterDTO2);
        analyticsCenterDTO1.setId(null);
        assertThat(analyticsCenterDTO1).isNotEqualTo(analyticsCenterDTO2);
    }
}
