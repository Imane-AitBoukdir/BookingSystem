package com.ensam.hotelalrbadr.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "paymentinfo")
public class paymentinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    @Column(name = "payment_id")
    private Integer paymentId; // Primary key

    @Column(name = "user_id", nullable = false)
    private Integer userId; // Foreign key to User (if applicable)

    @Column(name = "credit_card_number", nullable = false, length = 50)
    private String creditCardNumber;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "security_code", nullable = false, length = 50)
    private String securityCode;

    @Column(name = "billing_address", nullable = false)
    private String billingAddress;

    // Default constructor
    public paymentinfo() {
    }

    // Getters and Setters
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
}
