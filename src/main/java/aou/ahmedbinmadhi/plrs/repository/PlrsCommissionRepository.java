package aou.ahmedbinmadhi.plrs.repository;

import aou.ahmedbinmadhi.plrs.domain.PlrsCommission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlrsCommission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlrsCommissionRepository extends JpaRepository<PlrsCommission, Long> {}
