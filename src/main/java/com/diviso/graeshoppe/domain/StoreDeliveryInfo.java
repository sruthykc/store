package com.diviso.graeshoppe.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A StoreDeliveryInfo.
 */
@Entity
@Table(name = "store_delivery_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StoreDeliveryInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "starting_time")
    private ZonedDateTime startingTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @ManyToOne
    @JsonIgnoreProperties("storeDeliveryInfos")
    private Store store;

    @ManyToOne
    @JsonIgnoreProperties("storeDeliveryInfos")
    private StoreType storeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartingTime() {
        return startingTime;
    }

    public StoreDeliveryInfo startingTime(ZonedDateTime startingTime) {
        this.startingTime = startingTime;
        return this;
    }

    public void setStartingTime(ZonedDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public StoreDeliveryInfo endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Store getStore() {
        return store;
    }

    public StoreDeliveryInfo store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public StoreDeliveryInfo storeType(StoreType storeType) {
        this.storeType = storeType;
        return this;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
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
        StoreDeliveryInfo storeDeliveryInfo = (StoreDeliveryInfo) o;
        if (storeDeliveryInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storeDeliveryInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StoreDeliveryInfo{" +
            "id=" + getId() +
            ", startingTime='" + getStartingTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
