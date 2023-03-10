package aou.ahmedbinmadhi.plrs.domain;

import static org.assertj.core.api.Assertions.assertThat;

import aou.ahmedbinmadhi.plrs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyticsCenterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyticsCenter.class);
        AnalyticsCenter analyticsCenter1 = new AnalyticsCenter();
        analyticsCenter1.setId(1L);
        AnalyticsCenter analyticsCenter2 = new AnalyticsCenter();
        analyticsCenter2.setId(analyticsCenter1.getId());
        assertThat(analyticsCenter1).isEqualTo(analyticsCenter2);
        analyticsCenter2.setId(2L);
        assertThat(analyticsCenter1).isNotEqualTo(analyticsCenter2);
        analyticsCenter1.setId(null);
        assertThat(analyticsCenter1).isNotEqualTo(analyticsCenter2);
    }
}
