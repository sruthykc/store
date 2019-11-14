package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.StoreDeliveryInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StoreDeliveryInfo and its DTO StoreDeliveryInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {StoreMapper.class, StoreTypeMapper.class})
public interface StoreDeliveryInfoMapper extends EntityMapper<StoreDeliveryInfoDTO, StoreDeliveryInfo> {

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "storeType.id", target = "storeTypeId")
    StoreDeliveryInfoDTO toDto(StoreDeliveryInfo storeDeliveryInfo);

    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "storeTypeId", target = "storeType")
    StoreDeliveryInfo toEntity(StoreDeliveryInfoDTO storeDeliveryInfoDTO);

    default StoreDeliveryInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        StoreDeliveryInfo storeDeliveryInfo = new StoreDeliveryInfo();
        storeDeliveryInfo.setId(id);
        return storeDeliveryInfo;
    }
}
