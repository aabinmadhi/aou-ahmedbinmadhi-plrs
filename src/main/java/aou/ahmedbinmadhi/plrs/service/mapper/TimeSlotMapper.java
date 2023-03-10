package aou.ahmedbinmadhi.plrs.service.mapper;

import aou.ahmedbinmadhi.plrs.domain.LocationService;
import aou.ahmedbinmadhi.plrs.domain.TimeSlot;
import aou.ahmedbinmadhi.plrs.service.dto.LocationServiceDTO;
import aou.ahmedbinmadhi.plrs.service.dto.TimeSlotDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeSlot} and its DTO {@link TimeSlotDTO}.
 */
@Mapper(componentModel = "spring")
public interface TimeSlotMapper extends EntityMapper<TimeSlotDTO, TimeSlot> {
    @Mapping(target = "locationService", source = "locationService", qualifiedByName = "locationServiceId")
    TimeSlotDTO toDto(TimeSlot s);

    @Named("locationServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationServiceDTO toDtoLocationServiceId(LocationService locationService);
}
