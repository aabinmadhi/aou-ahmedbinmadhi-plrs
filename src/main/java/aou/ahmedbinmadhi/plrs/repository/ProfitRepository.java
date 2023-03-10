package aou.ahmedbinmadhi.plrs.repository;

import aou.ahmedbinmadhi.plrs.domain.Profit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfitRepository extends JpaRepository<Profit, Long> {}
