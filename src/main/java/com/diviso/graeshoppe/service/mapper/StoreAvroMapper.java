package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.StoreDTO;

import java.nio.ByteBuffer;
import java.time.ZonedDateTime;

import org.mapstruct.*;

/**
 * Mapper for the entity Store and its DTO StoreDTO.
 */
@Mapper(componentModel = "spring"/*, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS*//*, uses = {PropreitorMapper.class, StoreAddressMapper.class, StoreSettingsMapper.class, PreOrderSettingsMapper.class}*/)
public interface StoreAvroMapper extends AvroMapper<com.diviso.graeshoppe.store.avro.Store, StoreDTO> {
/*
    @Mapping(source = "propreitor.id", target = "propreitorId")
    @Mapping(source = "storeAddress.id", target = "storeAddressId")
    @Mapping(source = "storeSettings.id", target = "storeSettingsId")
    @Mapping(source = "preOrderSettings.id", target = "preOrderSettingsId")
    StoreDTO toAvro(Store store);

    @Mapping(source = "propreitorId", target = "propreitor")
    @Mapping(source = "storeAddressId", target = "storeAddress")
    @Mapping(source = "storeSettingsId", target = "storeSettings")
    @Mapping(source = "preOrderSettingsId", target = "preOrderSettings")
    @Mapping(target = "storeTypes", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "userRatings", ignore = true)
    @Mapping(target = "banners", ignore = true)
    @Mapping(target = "storeDeliveryInfos", ignore = true)
    Store toEntity(StoreDTO storeDTO);*/

	@Mapping(source = "image", target = "image", qualifiedByName = "byteArrayToByteBuffer")
	@Mapping(source = "openingTime", target = "openingTime", qualifiedByName = "zonedDateTimeToLong")
	@Mapping(source = "closingTime", target = "closingTime", qualifiedByName = "zonedDateTimeToLong")
	@Mapping(source = "maxDeliveryTime", target = "maxDeliveryTime", qualifiedByName = "zonedDateTimeToLong")
	 // @Mapping(target = "image", ignore =true)
	com.diviso.graeshoppe.store.avro.Store toAvro(StoreDTO storeDTO);
	
	
	
    default Store fromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
    
    @Named("byteArrayToByteBuffer") 
    public static ByteBuffer byteArrayToByteBuffer(byte[] image) { 
    	ByteBuffer byteBuffer = ByteBuffer.wrap(image);
        return byteBuffer;
    }
    @Named("zonedDateTimeToLong") 
    public static Long zonedDateTimeToLong(ZonedDateTime date) { 
        return date.toInstant().toEpochMilli(); 
    }
    
    
    
}
