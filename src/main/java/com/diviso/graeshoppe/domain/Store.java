package com.diviso.graeshoppe.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idp_code", nullable = false)
    private String idpCode;

    @NotNull
    @Column(name = "store_unique_id", nullable = false)
    private String storeUniqueId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "image_link", nullable = false)
    private String imageLink;

    @Column(name = "total_rating")
    private Double totalRating;

    @NotNull
    @Column(name = "lat_lon", nullable = false)
    private String latLon;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "contact_number")
    private Long contactNumber;

    @Column(name = "opening_time")
    private ZonedDateTime openingTime;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "closing_time")
    private ZonedDateTime closingTime;

    @Column(name = "info")
    private String info;

    @Column(name = "min_amount")
    private Double minAmount;

    @Column(name = "max_delivery_time")
    private ZonedDateTime maxDeliveryTime;

    @OneToOne
    @JoinColumn(unique = true)
    private Propreitor propreitor;

    @OneToOne
    @JoinColumn(unique = true)
    private StoreAddress storeAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private StoreSettings storeSettings;

    @OneToOne
    @JoinColumn(unique = true)
    private PreOrderSettings preOrderSettings;

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StoreType> storeTypes = new HashSet<>();
    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviews = new HashSet<>();
    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserRating> userRatings = new HashSet<>();
    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Banner> banners = new HashSet<>();
    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StoreDeliveryInfo> storeDeliveryInfos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdpCode() {
        return idpCode;
    }

    public Store idpCode(String idpCode) {
        this.idpCode = idpCode;
        return this;
    }

    public void setIdpCode(String idpCode) {
        this.idpCode = idpCode;
    }

    public String getStoreUniqueId() {
        return storeUniqueId;
    }

    public Store storeUniqueId(String storeUniqueId) {
        this.storeUniqueId = storeUniqueId;
        return this;
    }

    public void setStoreUniqueId(String storeUniqueId) {
        this.storeUniqueId = storeUniqueId;
    }

    public String getName() {
        return name;
    }

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Store imageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public Store totalRating(Double totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public String getLatLon() {
        return latLon;
    }

    public Store latLon(String latLon) {
        this.latLon = latLon;
        return this;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getLocationName() {
        return locationName;
    }

    public Store locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public Store contactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public ZonedDateTime getOpeningTime() {
        return openingTime;
    }

    public Store openingTime(ZonedDateTime openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(ZonedDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public String getEmail() {
        return email;
    }

    public Store email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getClosingTime() {
        return closingTime;
    }

    public Store closingTime(ZonedDateTime closingTime) {
        this.closingTime = closingTime;
        return this;
    }

    public void setClosingTime(ZonedDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getInfo() {
        return info;
    }

    public Store info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public Store minAmount(Double minAmount) {
        this.minAmount = minAmount;
        return this;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public ZonedDateTime getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public Store maxDeliveryTime(ZonedDateTime maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
        return this;
    }

    public void setMaxDeliveryTime(ZonedDateTime maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
    }

    public Propreitor getPropreitor() {
        return propreitor;
    }

    public Store propreitor(Propreitor propreitor) {
        this.propreitor = propreitor;
        return this;
    }

    public void setPropreitor(Propreitor propreitor) {
        this.propreitor = propreitor;
    }

    public StoreAddress getStoreAddress() {
        return storeAddress;
    }

    public Store storeAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    public void setStoreAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
    }

    public StoreSettings getStoreSettings() {
        return storeSettings;
    }

    public Store storeSettings(StoreSettings storeSettings) {
        this.storeSettings = storeSettings;
        return this;
    }

    public void setStoreSettings(StoreSettings storeSettings) {
        this.storeSettings = storeSettings;
    }

    public PreOrderSettings getPreOrderSettings() {
        return preOrderSettings;
    }

    public Store preOrderSettings(PreOrderSettings preOrderSettings) {
        this.preOrderSettings = preOrderSettings;
        return this;
    }

    public void setPreOrderSettings(PreOrderSettings preOrderSettings) {
        this.preOrderSettings = preOrderSettings;
    }

    public Set<StoreType> getStoreTypes() {
        return storeTypes;
    }

    public Store storeTypes(Set<StoreType> storeTypes) {
        this.storeTypes = storeTypes;
        return this;
    }

    public Store addStoreType(StoreType storeType) {
        this.storeTypes.add(storeType);
        storeType.setStore(this);
        return this;
    }

    public Store removeStoreType(StoreType storeType) {
        this.storeTypes.remove(storeType);
        storeType.setStore(null);
        return this;
    }

    public void setStoreTypes(Set<StoreType> storeTypes) {
        this.storeTypes = storeTypes;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Store reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Store addReview(Review review) {
        this.reviews.add(review);
        review.setStore(this);
        return this;
    }

    public Store removeReview(Review review) {
        this.reviews.remove(review);
        review.setStore(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<UserRating> getUserRatings() {
        return userRatings;
    }

    public Store userRatings(Set<UserRating> userRatings) {
        this.userRatings = userRatings;
        return this;
    }

    public Store addUserRating(UserRating userRating) {
        this.userRatings.add(userRating);
        userRating.setStore(this);
        return this;
    }

    public Store removeUserRating(UserRating userRating) {
        this.userRatings.remove(userRating);
        userRating.setStore(null);
        return this;
    }

    public void setUserRatings(Set<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public Set<Banner> getBanners() {
        return banners;
    }

    public Store banners(Set<Banner> banners) {
        this.banners = banners;
        return this;
    }

    public Store addBanner(Banner banner) {
        this.banners.add(banner);
        banner.setStore(this);
        return this;
    }

    public Store removeBanner(Banner banner) {
        this.banners.remove(banner);
        banner.setStore(null);
        return this;
    }

    public void setBanners(Set<Banner> banners) {
        this.banners = banners;
    }

    public Set<StoreDeliveryInfo> getStoreDeliveryInfos() {
        return storeDeliveryInfos;
    }

    public Store storeDeliveryInfos(Set<StoreDeliveryInfo> storeDeliveryInfos) {
        this.storeDeliveryInfos = storeDeliveryInfos;
        return this;
    }

    public Store addStoreDeliveryInfo(StoreDeliveryInfo storeDeliveryInfo) {
        this.storeDeliveryInfos.add(storeDeliveryInfo);
        storeDeliveryInfo.setStore(this);
        return this;
    }

    public Store removeStoreDeliveryInfo(StoreDeliveryInfo storeDeliveryInfo) {
        this.storeDeliveryInfos.remove(storeDeliveryInfo);
        storeDeliveryInfo.setStore(null);
        return this;
    }

    public void setStoreDeliveryInfos(Set<StoreDeliveryInfo> storeDeliveryInfos) {
        this.storeDeliveryInfos = storeDeliveryInfos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Store store = (Store) o;
        if (store.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), store.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Store{" +
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
            "}";
    }
}
