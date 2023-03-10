package aou.ahmedbinmadhi.plrs.domain;

import static org.assertj.core.api.Assertions.assertThat;

import aou.ahmedbinmadhi.plrs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlrsCommissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlrsCommission.class);
        PlrsCommission plrsCommission1 = new PlrsCommission();
        plrsCommission1.setId(1L);
        PlrsCommission plrsCommission2 = new PlrsCommission();
        plrsCommission2.setId(plrsCommission1.getId());
        assertThat(plrsCommission1).isEqualTo(plrsCommission2);
        plrsCommission2.setId(2L);
        assertThat(plrsCommission1).isNotEqualTo(plrsCommission2);
        plrsCommission1.setId(null);
        assertThat(plrsCommission1).isNotEqualTo(plrsCommission2);
    }
}
