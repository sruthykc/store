package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.DeliveryTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DeliveryType and its DTO DeliveryTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeliveryTypeMapper extends EntityMapper<DeliveryTypeDTO, DeliveryType> {



    default DeliveryType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeliveryType deliveryType = new DeliveryType();
        deliveryType.setId(id);
        return deliveryType;
    }
}
