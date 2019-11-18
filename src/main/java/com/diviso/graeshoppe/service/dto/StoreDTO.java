package com.diviso.graeshoppe.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Store entity.
 */
public class StoreDTO implements Serializable {

    private Long id;

    @NotNull
    private String idpCode;

    @NotNull
    private String storeUniqueId;

    @NotNull
    private String name;
    
    @Lob
    private byte[] image;

    @NotNull
    private String imageLink;

    private Double totalRating;

    @NotNull
    private String latLon;

    private String locationName;

    private Long contactNumber;

    private ZonedDateTime openingTime;

    @NotNull
    private String email;

    private ZonedDateTime closingTime;

    private String info;

    private Double minAmount;

    private ZonedDateTime maxDeliveryTime;
    
    private String zoneId;


    private Long propreitorId;

    private Long storeAddressId;

    private Long storeSettingsId;

    private Long preOrderSettingsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdpCode() {
        return idpCode;
    }

    public void setIdpCode(String idpCode) {
        this.idpCode = idpCode;
    }

    public String getStoreUniqueId() {
        return storeUniqueId;
    }

    public void setStoreUniqueId(String storeUniqueId) {
        this.storeUniqueId = storeUniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public ZonedDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(ZonedDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(ZonedDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public ZonedDateTime getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public void setMaxDeliveryTime(ZonedDateTime maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
    }

    public Long getPropreitorId() {
        return propreitorId;
    }

    public void setPropreitorId(Long propreitorId) {
        this.propreitorId = propreitorId;
    }

    public Long getStoreAddressId() {
        return storeAddressId;
    }

    public void setStoreAddressId(Long storeAddressId) {
        this.storeAddressId = storeAddressId;
    }

	public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getStoreSettingsId() {
        return storeSettingsId;
    }

    public void setStoreSettingsId(Long storeSettingsId) {
        this.storeSettingsId = storeSettingsId;
    }

    public Long getPreOrderSettingsId() {
        return preOrderSettingsId;
    }

    public void setPreOrderSettingsId(Long preOrderSettingsId) {
        this.preOrderSettingsId = preOrderSettingsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StoreDTO storeDTO = (StoreDTO) o;
        if (storeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	@Override
    public String toString() {
        return "StoreDTO{" +
            "id=" + getId() +
            ", idpCode='" + getIdpCode() + "'" +
            ", storeUniqueId='" + getStoreUniqueId() + "'" +
            ", name='" + getName() + "'" +
            ", imageLink='" + getImageLink() + "'" +
            ", totalRating=" + getTotalRating() +
            ", latLon='" + getLatLon() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", contactNumber=" + getContactNumber() +
            ", openingTime='" + getOpeningTime() + "'" +
            ", email='" + getEmail() + "'" +
            ", closingTime='" + getClosingTime() + "'" +
            ", info='" + getInfo() + "'" +
            ", minAmount=" + getMinAmount() +
            ", maxDeliveryTime='" + getMaxDeliveryTime() + "'" +
            ", propreitor=" + getPropreitorId() +
            ", storeAddress=" + getStoreAddressId() +
            ", storeSettings=" + getStoreSettingsId() +
            ", preOrderSettings=" + getPreOrderSettingsId() +
            "}";
    }
}
