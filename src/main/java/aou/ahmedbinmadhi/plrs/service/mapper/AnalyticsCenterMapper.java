package aou.ahmedbinmadhi.plrs.service.mapper;

import aou.ahmedbinmadhi.plrs.domain.AnalyticsCenter;
import aou.ahmedbinmadhi.plrs.service.dto.AnalyticsCenterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnalyticsCenter} and its DTO {@link AnalyticsCenterDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnalyticsCenterMapper extends EntityMapper<AnalyticsCenterDTO, AnalyticsCenter> {}
