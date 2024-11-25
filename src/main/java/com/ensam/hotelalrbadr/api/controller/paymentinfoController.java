package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.paymentinfo;
import com.ensam.hotelalrbadr.api.repository.PaymentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paymentinfo")
public class paymentinfoController {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    // Get all payment info
    @GetMapping
    public List<paymentinfo> getAllPaymentInfo() {
        return paymentInfoRepository.findAll();
    }

    // Get payment info by paymentId
    @GetMapping("/{payment_id}")
    public ResponseEntity<paymentinfo> getPaymentInfoById(@PathVariable("payment_id") Integer paymentId) {
        Optional<paymentinfo> paymentInfo = paymentInfoRepository.findById(paymentId);
        return paymentInfo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new payment info
    @PostMapping
    public ResponseEntity<paymentinfo> createPaymentInfo(@RequestBody paymentinfo paymentInfo) {
        paymentinfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);
        return new ResponseEntity<>(savedPaymentInfo, HttpStatus.CREATED);
    }

    // Update an existing payment info
    @PutMapping("/{payment_id}")
    public ResponseEntity<paymentinfo> updatePaymentInfo(@PathVariable("payment_id") Integer paymentId, @RequestBody paymentinfo paymentInfoDetails) {
        if (!paymentInfoRepository.existsById(paymentId)) {
            return ResponseEntity.notFound().build();
        }

        // Set the paymentId to match the path variable 'paymentId'
        paymentInfoDetails.setPaymentId(paymentId);

        // Save and update the payment info in the database
        paymentinfo updatedPaymentInfo = paymentInfoRepository.save(paymentInfoDetails);
        return ResponseEntity.ok(updatedPaymentInfo);
    }

    // Delete a payment info by paymentId
    @DeleteMapping("/{payment_id}")
    public ResponseEntity<Void> deletePaymentInfo(@PathVariable("payment_id") Integer paymentId) {
        if (!paymentInfoRepository.existsById(paymentId)) {
            return ResponseEntity.notFound().build();
        }
        paymentInfoRepository.deleteById(paymentId);
        return ResponseEntity.noContent().build();
    }
}
