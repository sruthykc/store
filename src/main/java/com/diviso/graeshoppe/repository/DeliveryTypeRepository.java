package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.DeliveryType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeliveryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {

}
