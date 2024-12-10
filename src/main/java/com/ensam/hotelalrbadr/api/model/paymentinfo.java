package com.ensam.hotelalrbadr.api.model;

import java.time.LocalDate;

// This class represents the payment information in your database
// It matches the PaymentInfo table structure exactly
public class paymentinfo {
    // Database fields following your schema
    private Long payment_id;        // Primary key
    private Long user_id;           // Foreign key to Users table
    private String credit_card_number;
    private LocalDate expiration_date;
    private String security_code;
    private String billing_address;

    // Default constructor
    public paymentinfo() {}

    // Full constructor for all fields except ID (which is auto-generated)
    public paymentinfo(Long user_id, String credit_card_number, LocalDate expiration_date,
                       String security_code, String billing_address) {
        this.user_id = user_id;
        this.credit_card_number = credit_card_number;
        this.expiration_date = expiration_date;
        this.security_code = security_code;
        this.billing_address = billing_address;
    }

    // Getter and setter methods following your naming convention
    public Long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getCredit_card_number() {
        return credit_card_number;
    }

    public void setCredit_card_number(String credit_card_number) {
        this.credit_card_number = credit_card_number;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    // Override toString() method with security in mind
    @Override
    public String toString() {
        return "paymentinfo{" +
                "payment_id=" + payment_id +
                ", user_id=" + user_id +
                // Only show last 4 digits of card number for security
                ", credit_card_number='****" +
                credit_card_number.substring(Math.max(0, credit_card_number.length() - 4)) + '\'' +
                ", expiration_date=" + expiration_date +
                ", billing_address='" + billing_address + '\'' +
                // Security code is intentionally excluded for security
                '}';
    }
}