package com.diviso.graeshoppe.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StoreDeliveryInfo entity.
 */
public class StoreDeliveryInfoDTO implements Serializable {

    private Long id;

    private ZonedDateTime startingTime;

    private ZonedDateTime endTime;


    private Long storeId;

    private Long storeTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(ZonedDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Long storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StoreDeliveryInfoDTO storeDeliveryInfoDTO = (StoreDeliveryInfoDTO) o;
        if (storeDeliveryInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storeDeliveryInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StoreDeliveryInfoDTO{" +
            "id=" + getId() +
            ", startingTime='" + getStartingTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", store=" + getStoreId() +
            ", storeType=" + getStoreTypeId() +
            "}";
    }
}
