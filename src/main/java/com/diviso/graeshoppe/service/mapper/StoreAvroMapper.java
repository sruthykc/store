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
public interface StoreAvroMapper extends AvroMapper<com.diviso.graeshoppe.store.avro.StoreInfo, StoreDTO> {


	@Mapping(source = "image", target = "image", qualifiedByName = "byteArrayToByteBuffer"/*,ignore=true*/)
	@Mapping(source = "openingTime", target = "openingTime", qualifiedByName = "openingTimeToLong"/*,ignore=true*/)
	@Mapping(source = "closingTime", target = "closingTime", qualifiedByName = "closingTimeToLong"/*,ignore=true*/)
	@Mapping(source = "maxDeliveryTime", target = "maxDeliveryTime", qualifiedByName = "maxDeliveryTimeToLong"/*,ignore=true*/)
	/*@Mapping(source = "id", target = "storeCode")*/
	com.diviso.graeshoppe.store.avro.StoreInfo toAvro(StoreDTO storeDTO);
	
	
	
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
    	 if (image == null) {
             return null;
         }
    	ByteBuffer byteBuffer = ByteBuffer.wrap(image);
        return byteBuffer;
    }
    @Named("openingTimeToLong") 
    public static Long openingTimeToLong(ZonedDateTime opening) { 
    	Long value = 0L;
    	 if (opening.equals(null)) {
             return value;
         }
    	 value=opening.toInstant().toEpochMilli();
        return  value;
    }
    @Named("closingTimeToLong") 
    public static Long closingTimeToLong(ZonedDateTime closing) { 
    	Long value = 0L;
    	if (closing.equals(null)) {
             return value;
         }
    value = closing.toInstant().toEpochMilli(); 
        return value;
    }
    
    @Named("maxDeliveryTimeToLong") 
    public static Long maxDeliveryToLong(ZonedDateTime maxDelivery) { 
    	Long value = 0L;
    	 if (maxDelivery.equals(null)) {
            
    		 return value;
         }
    	value = maxDelivery.toInstant().toEpochMilli(); 
        return value; 
    }
}
