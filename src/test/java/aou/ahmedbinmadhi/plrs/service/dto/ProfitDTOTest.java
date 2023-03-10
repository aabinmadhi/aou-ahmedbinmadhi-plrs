package aou.ahmedbinmadhi.plrs.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import aou.ahmedbinmadhi.plrs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfitDTO.class);
        ProfitDTO profitDTO1 = new ProfitDTO();
        profitDTO1.setId(1L);
        ProfitDTO profitDTO2 = new ProfitDTO();
        assertThat(profitDTO1).isNotEqualTo(profitDTO2);
        profitDTO2.setId(profitDTO1.getId());
        assertThat(profitDTO1).isEqualTo(profitDTO2);
        profitDTO2.setId(2L);
        assertThat(profitDTO1).isNotEqualTo(profitDTO2);
        profitDTO1.setId(null);
        assertThat(profitDTO1).isNotEqualTo(profitDTO2);
    }
}
