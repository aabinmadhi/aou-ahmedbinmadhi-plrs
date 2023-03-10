package aou.ahmedbinmadhi.plrs.domain;

import static org.assertj.core.api.Assertions.assertThat;

import aou.ahmedbinmadhi.plrs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profit.class);
        Profit profit1 = new Profit();
        profit1.setId(1L);
        Profit profit2 = new Profit();
        profit2.setId(profit1.getId());
        assertThat(profit1).isEqualTo(profit2);
        profit2.setId(2L);
        assertThat(profit1).isNotEqualTo(profit2);
        profit1.setId(null);
        assertThat(profit1).isNotEqualTo(profit2);
    }
}
