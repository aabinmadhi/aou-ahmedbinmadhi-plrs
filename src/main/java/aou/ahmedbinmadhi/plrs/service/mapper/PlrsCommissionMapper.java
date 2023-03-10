package aou.ahmedbinmadhi.plrs.service.mapper;

import aou.ahmedbinmadhi.plrs.domain.PlrsCommission;
import aou.ahmedbinmadhi.plrs.service.dto.PlrsCommissionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlrsCommission} and its DTO {@link PlrsCommissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlrsCommissionMapper extends EntityMapper<PlrsCommissionDTO, PlrsCommission> {}
