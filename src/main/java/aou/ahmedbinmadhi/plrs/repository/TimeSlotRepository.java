package aou.ahmedbinmadhi.plrs.repository;

import aou.ahmedbinmadhi.plrs.domain.TimeSlot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TimeSlot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {}
