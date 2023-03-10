package aou.ahmedbinmadhi.plrs.repository;

import aou.ahmedbinmadhi.plrs.domain.LocationService;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LocationService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationServiceRepository extends JpaRepository<LocationService, Long> {
    @Query("select locationService from LocationService locationService where locationService.internalUser.login = ?#{principal.username}")
    List<LocationService> findByInternalUserIsCurrentUser();
}
