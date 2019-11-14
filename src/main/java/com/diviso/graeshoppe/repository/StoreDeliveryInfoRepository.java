package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.StoreDeliveryInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StoreDeliveryInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreDeliveryInfoRepository extends JpaRepository<StoreDeliveryInfo, Long> {

}
