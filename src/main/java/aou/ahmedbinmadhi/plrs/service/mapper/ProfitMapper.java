package aou.ahmedbinmadhi.plrs.service.mapper;

import aou.ahmedbinmadhi.plrs.domain.Booking;
import aou.ahmedbinmadhi.plrs.domain.Profit;
import aou.ahmedbinmadhi.plrs.service.dto.BookingDTO;
import aou.ahmedbinmadhi.plrs.service.dto.ProfitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profit} and its DTO {@link ProfitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfitMapper extends EntityMapper<ProfitDTO, Profit> {
    @Mapping(target = "booking", source = "booking", qualifiedByName = "bookingId")
    ProfitDTO toDto(Profit s);

    @Named("bookingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookingDTO toDtoBookingId(Booking booking);
}
