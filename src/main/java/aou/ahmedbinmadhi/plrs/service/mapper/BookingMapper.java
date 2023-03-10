package aou.ahmedbinmadhi.plrs.service.mapper;

import aou.ahmedbinmadhi.plrs.domain.Booking;
import aou.ahmedbinmadhi.plrs.domain.LocationService;
import aou.ahmedbinmadhi.plrs.domain.User;
import aou.ahmedbinmadhi.plrs.service.dto.BookingDTO;
import aou.ahmedbinmadhi.plrs.service.dto.LocationServiceDTO;
import aou.ahmedbinmadhi.plrs.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Booking} and its DTO {@link BookingDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookingMapper extends EntityMapper<BookingDTO, Booking> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    @Mapping(target = "locationService", source = "locationService", qualifiedByName = "locationServiceId")
    BookingDTO toDto(Booking s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("locationServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationServiceDTO toDtoLocationServiceId(LocationService locationService);
}
