package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.model.paymentinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<paymentinfo, Integer> {
    // You can add custom query methods here if needed
}
