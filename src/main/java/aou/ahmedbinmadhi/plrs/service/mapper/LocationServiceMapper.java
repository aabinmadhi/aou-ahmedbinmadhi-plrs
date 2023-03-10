package aou.ahmedbinmadhi.plrs.service.mapper;

import aou.ahmedbinmadhi.plrs.domain.LocationService;
import aou.ahmedbinmadhi.plrs.domain.User;
import aou.ahmedbinmadhi.plrs.service.dto.LocationServiceDTO;
import aou.ahmedbinmadhi.plrs.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationService} and its DTO {@link LocationServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationServiceMapper extends EntityMapper<LocationServiceDTO, LocationService> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    LocationServiceDTO toDto(LocationService s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
