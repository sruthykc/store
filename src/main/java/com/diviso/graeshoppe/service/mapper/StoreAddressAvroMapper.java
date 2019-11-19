package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.StoreAddressDTO;
import com.diviso.graeshoppe.service.dto.StoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StoreAddress and its DTO StoreAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreAddressAvroMapper extends EntityMapper<com.diviso.graeshoppe.store.avro.StoreAddress, StoreAddress> {

	com.diviso.graeshoppe.store.avro.StoreAddress toAvro(StoreAddressDTO storeAddress);

    default StoreAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        StoreAddress storeAddress = new StoreAddress();
        storeAddress.setId(id);
        return storeAddress;
    }
}
