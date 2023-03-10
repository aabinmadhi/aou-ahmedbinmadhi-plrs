package aou.ahmedbinmadhi.plrs.repository;

import aou.ahmedbinmadhi.plrs.domain.AnalyticsCenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnalyticsCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyticsCenterRepository extends JpaRepository<AnalyticsCenter, Long> {}
